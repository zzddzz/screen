package com.east.sword.screen.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.config.ConstantConfig;
import com.east.sword.screen.config.ScheduleConfig;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.entity.ScreenFtp;
import com.east.sword.screen.msg.IMsgService;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.service.IScreenFtpService;
import com.east.sword.screen.service.IScreenService;
import com.east.sword.screen.util.FileUtil;
import com.east.sword.screen.util.ftp.FTPRouter;
import com.east.sword.screen.util.http.HttpClient;
import com.east.sword.screen.vo.KltRoute;
import com.east.sword.screen.vo.VsnJson;
import com.east.sword.screen.vo.VsnPlay;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时扫描任务
 *
 * @CreateDate 22:17 2020/2/17.
 * @Author ZZD
 */
@Component
@Slf4j
public class ScanJob {

    private static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Autowired
    private ScheduleConfig scheduleConfig;

    @Autowired
    private FTPRouter ftpRouter;

    @Autowired
    private KltRoute kltRoute;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ConstantConfig constantConfig;

    @Autowired
    private IScreenService screenService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IScreenFtpService screenFtpService;

    @Qualifier("routerMsgService")
    @Autowired()
    private IMsgService msgService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 轮询数据库中的大屏配置的cron ,保持线程内信息一致
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void syncDbScreenCron() {
        try {
            EntityWrapper<Screen> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("enable", "1");
            List<Screen> screenList = screenService.selectList(entityWrapper);
            Map<String, String> cacheKeyMap = scheduleConfig.getCacheCronMap();

            //更新变更的cron
            for (Screen screen : screenList) {
                String cacheCron = scheduleConfig.getTaskCron(String.valueOf(screen.getNo()));

                //更新screen cron
                if (!screen.getScheduleCron().equals(cacheCron)) {
                    scheduleConfig.resetTriggerTask(
                            String.valueOf(screen.getNo()),
                            new TriggerTask(
                                    () -> msgService.sendMsg(screen),
                                    new CronTrigger(screen.getScheduleCron())
                            ),
                            screen.getScheduleCron()
                    );
                }

                //缓存中不含的添加
                if (!cacheKeyMap.keySet().contains(String.valueOf(screen.getNo()))) {
                    scheduleConfig.addTriggerTask(
                            String.valueOf(screen.getNo()),
                            new TriggerTask(
                                    () -> msgService.sendMsg(screen),
                                    new CronTrigger(screen.getScheduleCron())
                            ),
                            screen.getScheduleCron()
                    );
                }

            }

            //缓存中多余的删除
            for (String key : cacheKeyMap.keySet()) {
                boolean needDel = true;
                for (Screen screen : screenList) {
                    if (String.valueOf(screen.getNo()).equals(key)) {
                        needDel = false;
                    }
                }
                if (needDel) {
                    scheduleConfig.cancelTriggerTask(key);
                }
            }
        } catch (Exception e) {
            log.error("刷新数据库中的定时任务异常:{}", e);
        }


    }

    /**
     * FTP WEB服务 大屏播放列表 资源同步(仅用于卡莱特大屏同步FTP资源)
     * 1 遍历ftp-srceen 关系,取出可以遍历的FTP源
     * 2 对FTP源进行遍历,按照符合规定的screen 规则（ftp 文件倒序,取最新的几张图片）,取出对应的图片数量
     * 3 根据unicode 值 插入/更新 数据库Resource 信息
     * 4 生成VSN pic 文件存储到服务器并触发上传
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void syncFtpFile() {

        log.info("Begin SyncFtpFile JOB...");
        List<ScreenFtp> screenFtpList = screenFtpService.selectInfoAllList(Screen.STATUS_ENABLE);
        for (ScreenFtp screenFtp : screenFtpList) {
            Screen screen = screenService.selectById(screenFtp.getScreenId());
            try {

                //判断设置的FTP 同步时间是否符合触发标准.
                String now = DateFormatUtils.format(new Date(), "HH:mm:ss");
                if (!compTime(now, screenFtp.getBegTime()) && compTime(screenFtp.getEndTime(), now)) continue;

                //遍历Ftp文件
                List<FTPFile> files = ftpRouter.getFTPClient(screenFtp.getUnicode()).getPicFiles("./", screen.getPlayPicNum(), -1, screen.getRegexChar());
                for (FTPFile file : files) {

                    //转换FTP资源对象
                    Resource resource = this.convertFtpFileToResource(screen, file);
                    if (null == resource) continue;

                    //判断资源是否存在更新图片信息
                    int resourceExistNum = resourceService.getNumOfResource(resource.getUnicode());
                    if (resourceExistNum > 0) continue;


                    //生成pic,vsn文件,触发重新上传资源
                    ftpRouter.getFTPClient(screenFtp.getUnicode()).downFile(resource.getOriginName(),
                            resource.getFilePath() + resource.getFileName());

                    String picFilePath = resource.getFilePath() + resource.getFileName();
                    JsonObject vsnJson = VsnJson.getVsn(VsnJson.VSN_PIC, resource.getVsnName(), resource.getFileName(), picFilePath);
                    String content = gson.toJson(vsnJson);
                    FileUtil.createFile(resource.getFilePath(), resource.getVsnName(), content);

                    //触发上传新资源(如果vsn 重名,则会覆盖上传播放盒)
                    String sendUrl = kltRoute.uploadRountPath(screen.getUri(), resource.getVsnName());
                    httpClient.httpPostMedia(sendUrl, resource);

                    //是否更新resource 信息
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("no", screen.getNo());
                    entityWrapper.eq("originName", resource.getOriginName());
                    entityWrapper.ne("status", Resource.STATUS_ISDEL);
                    Resource resourceDb = resourceService.selectOne(entityWrapper);
                    if (null != resourceDb) {
                        resourceDb.setUnicode(resource.getUnicode());
                        resourceDb.setCreateDate(resource.getCreateDate());
                        resourceDb.setResourceDateTime(resource.getResourceDateTime());
                        resourceService.updateById(resourceDb);
                    } else {
                        resourceService.insert(resource);
                    }

                }
            } catch (Exception e) {
                log.error("同步FTP文件异常:{}", e);
            }
        }

    }


    /**
     * 守护定时-- 大屏和本地资源一致
     * 【本地资源】【大屏资源】【Redis 序列】一致(仅同步卡莱特数据)
     * 以本地资源作为标准
     * 需要删除大屏资源
     * 需要新增大屏资源(超出大屏播放限制/未超出播放限制)
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void synResourceToScreen() {
        try {
            EntityWrapper<Screen> screenQuary = new EntityWrapper();
            screenQuary.eq("enable", Screen.STATUS_ENABLE);
            screenQuary.eq("type", Screen.TYPE_KLT);
            List<Screen> screenList = screenService.selectList(screenQuary);

            for (Screen screen : screenList) {

                //查询合法的播放资源,资源按照创建时间倒序,取出合法的数量
                EntityWrapper<Resource> entityWrapper = new EntityWrapper();
                entityWrapper.eq("no", screen.getNo());
                entityWrapper.eq("status", Resource.STATUS_ENABLE);
                entityWrapper.orderBy("createDate", false);//倒序
                List<Resource> resourceList = resourceService.selectList(entityWrapper);

                List<Resource> picResourceList = resourceList.stream()
                        .filter(meta -> Resource.SRC_SYNC.equals(meta.getSrcType())).collect(Collectors.toList());

                List<Resource> cutResourceList = resourceList.stream()
                        .filter(meta -> Resource.SRC_CUT.equals(meta.getSrcType())).collect(Collectors.toList());

                List<VsnPlay> vsnPlayList = msgService.getRemoteScreenPlayList(screen);
                List<String> vsnNamePlayList = vsnPlayList.stream().map(VsnPlay::getName).collect(Collectors.toList());

                List<Resource> illPlayResource = new ArrayList<>();
                List<Resource> legalPlayResource = new ArrayList<>();
                if (null != picResourceList && picResourceList.size() > screen.getPlayPicNum()) {
                    legalPlayResource.addAll(picResourceList.subList(0, screen.getPlayPicNum()));
                    illPlayResource.addAll(picResourceList.subList(screen.getPlayPicNum(), picResourceList.size()));
                } else {
                    legalPlayResource.addAll(picResourceList);
                }
                legalPlayResource.addAll(cutResourceList);
                List<String> legalNameList = legalPlayResource.stream().map(Resource::getVsnName).collect(Collectors.toList());

                //更新合法的资源
                legalPlayResource.forEach(legalResource -> {
                    if (vsnNamePlayList.contains(legalResource.getVsnName())) {
                        return;
                    } else {//上传资源
                        msgService.putResource(screen, legalResource);
                        log.info("KLT守护进程上传 screenNo:{}--url:{}", screen.getNo(), screen.getUri() + legalResource.getVsnName());
                    }
                });

                //删除过期的资源(只针对FTP 图片)
                vsnPlayList.forEach(vsnPlay -> {
                    if (!legalNameList.contains(vsnPlay.getName())) {
                        Resource resourceData = new Resource();
                        resourceData.setVsnName(vsnPlay.getName());
                        msgService.putDownResource(screen, resourceData);
                        log.info("KLT守护进程删除 screenNo:{}--url:{}", screen.getNo(), screen.getUri() + vsnPlay.getName());
                    }
                });

                // 停播超过最大播放数量限制的图片
                illPlayResource.forEach(resource -> {
                    msgService.putDownResource(screen, resource);
                });

            }
        } catch (Exception e) {
            log.error("KLT守护进程异常:{}", e);
        }
    }


    /**
     * 检测卡莱特诱导屏状态
     */
    @Scheduled(cron = "*/20 * * * * ?")
    public void syncKltPowerStatus() {
        EntityWrapper<Screen> screenQuary = new EntityWrapper();
        screenQuary.eq("enable", Screen.STATUS_ENABLE);
        screenQuary.eq("type", Screen.TYPE_KLT);
        List<Screen> screenList = screenService.selectList(screenQuary);
        screenList.forEach(meta->{
            int status = 0;
            try {
                msgService.getPowerStatus(meta);
            } catch (Exception e) {
                log.error("获取KLT powerStatus 异常:{}",e.getMessage());
                status = -1;

            } finally {
                if (status == -1 && !Screen.STATUS_ERROR.equals(meta.getEnable())) {
                    meta.setEnable(Screen.STATUS_ERROR);
                    screenService.updateById(meta);
                }
                if (status == 0 && Screen.STATUS_ERROR.equals(meta.getEnable())) {
                    meta.setEnable(Screen.STATUS_ENABLE);
                    screenService.updateById(meta);
                }
            }

        });

    }

    /**
     * FTP 文件转换后的Resource
     *
     * @param screenList
     * @param originFileName
     * @return
     */
    public Resource convertFtpFileToResource(Screen screen, FTPFile ftpFile) {
        Resource resource = null;
        String originFileName = ftpFile.getName();

        //唯一标识  大屏IP + 原始文件名 + 文件大小 + 上传时间
        String unicode = DigestUtils.md5Hex(screen.getHost() + originFileName + ftpFile.getSize() + ftpFile.getTimestamp().getTimeInMillis());

        //符合文件匹配规则,获取Resource
        if (FileUtil.validatePicOfScreen(originFileName, screen.getRegexChar())) {

            resource = new Resource();
            //新文件名 = 大屏编号 + 原始文件名
            String picName = originFileName.substring(0, originFileName.lastIndexOf("."));
            String picType = originFileName.substring(originFileName.lastIndexOf(".") + 1, originFileName.length());

            resource.setNo(screen.getNo());
            resource.setCreateDate(new Date());
            resource.setFilePath(constantConfig.fileCache + screen.getNo() + File.separator);

            resource.setFileName(DigestUtils.md5Hex(picName) + "." + picType);
            resource.setVsnName(DigestUtils.md5Hex(picName) + ".vsn");
            resource.setOriginName(originFileName);

            resource.setType(Resource.TYPE_PIC);//图片类
            resource.setSrcType(Resource.SRC_SYNC);
            resource.setStatus(Resource.STATUS_ENABLE);//默认设置可用
            resource.setUnicode(unicode);//设置唯一标示


            //设置FTP中文件上传时间
            //ftpFile.getTimestamp().getTimeZone().getOffset(0); utc 时间加上时差
            Date date = new Date(ftpFile.getTimestamp().getTimeInMillis());
            String ftpResourceDate = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
            resource.setResourceDateTime(ftpResourceDate);
        }
        return resource;
    }


    /**
     * 判断两个HH:mm:ss的大小
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean compTime(String s1, String s2) {
        try {
            if (s1.indexOf(":") < 0 || s1.indexOf(":") < 0) {
                System.out.println("格式不正确");
            } else {
                String[] array1 = s1.split(":");
                int total1 = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60 + Integer.valueOf(array1[2]);
                String[] array2 = s2.split(":");
                int total2 = Integer.valueOf(array2[0]) * 3600 + Integer.valueOf(array2[1]) * 60 + Integer.valueOf(array2[2]);
                return total1 - total2 > 0 ? true : false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            return true;
        }
        return false;

    }


}
