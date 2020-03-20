package com.east.sword.screen.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.config.ConstantConfig;
import com.east.sword.screen.config.ScheduleConfig;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.msg.IMsgService;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.service.IScreenService;
import com.east.sword.screen.util.FileUtil;
import com.east.sword.screen.util.ftp.FTPUtils;
import com.east.sword.screen.util.http.HttpClient;
import com.east.sword.screen.vo.KltRoute;
import com.east.sword.screen.vo.VsnJson;
import com.east.sword.screen.vo.VsnPlay;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

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
    private FTPUtils ftpUtils;

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

    @Qualifier("kltMsgService")
    @Autowired()
    private IMsgService msgService;

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
                if (!screen.getCron().equals(cacheCron)) {
                    scheduleConfig.resetTriggerTask(
                            String.valueOf(screen.getNo()),
                            new TriggerTask(
                                    () -> msgService.sendMsg(screen),
                                    new CronTrigger(screen.getCron())
                            ),
                            screen.getCron()
                    );
                }

                //缓存中不含的添加
                if (!cacheKeyMap.keySet().contains(String.valueOf(screen.getNo()))) {
                    scheduleConfig.addTriggerTask(
                            String.valueOf(screen.getNo()),
                            new TriggerTask(
                                    () -> msgService.sendMsg(screen),
                                    new CronTrigger(screen.getCron())
                            ),
                            screen.getCron()
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
     * FTP WEB服务 大屏播放列表 资源同步
     * 1 拉取FTP 中的文件遍历
     * 2 生成VSN pic 文件到服务器
     * 3 插入数据库播放资源
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void syncFtpFile() {
        try {
            log.info("syncFtpFile...{}", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            EntityWrapper entityWrapper = new EntityWrapper();
            List<Screen> screenList = screenService.selectList(entityWrapper);

            //遍历Ftp文件
            FTPFile[] files = ftpUtils.getFiles(".");
            for (FTPFile file : files) {
                if (!FileUtil.isPic(file)) {
                    continue;
                }

                //转换FTP资源对象
                Resource resource = new Resource();
                resource = this.convertFileName(screenList, file, resource);

                //转存pic,vsn, save data
                if (StringUtils.isNotBlank(resource.getFileName())) {

                    //不存在的数据,插入&生成文件 BMP/VSN
                    int resourceExistNum = resourceService.getNumOfResource(resource.getOriginName(), resource.getResourceDateTime());
                    if (resourceExistNum == 0) {

                        ftpUtils.downFile(resource.getOriginName(), resource.getFilePath() + resource.getFileName());

                        //生成vsn 文件
                        String picFilePath = resource.getFilePath() + resource.getFileName();
                        JsonObject vsnJson = VsnJson.getVsn(VsnJson.VSN_PIC, resource.getVsnName(), resource.getFileName(), picFilePath);
                        String content = gson.toJson(vsnJson);
                        FileUtil.createFile(resource.getFilePath(), resource.getVsnName(), content);

                        resourceService.insert(resource);

                        //更新大屏资源,判断大屏的限制的播放条数,1可直接上传,2 删除超过数量限制的过期资源,上传新资源
                        Screen screen = screenService.selectById(resource.getNo());
                        String sendUrl = kltRoute.uploadRountPath(screen.getUri(), resource.getVsnName());
                        String response = httpClient.httpPostMedia(sendUrl, resource);
                        log.info("FTP扫描上传大屏...:{}", response);

                        List<VsnPlay> vsnPlayList = msgService.getRemoteScreenPlayList(screen);
                        if (vsnPlayList.size() > screen.getPlayPicNum()) {//删除过期资源

                            //大屏返回的vsn列表按照时间正序排列
                            List<VsnPlay> needDelVsnList = vsnPlayList.subList(0, vsnPlayList.size() - screen.getPlayPicNum());
                            needDelVsnList.forEach(vsnPlay -> {
                                msgService.putDownResource(screen.getUri(), vsnPlay.getName());
                                log.info("FTP扫描上传后删除过期资源,url:{}", screen.getUri() + vsnPlay.getName());
                            });
                        }
                    }
                }

            }
        } catch (Exception e) {
            log.error("同步FTP文件异常:{}", e);
        }
    }

    /**
     * 同步本地资源和大屏资源
     * 1 获取本地待播放资源
     * 2 获取大屏缓存资源
     * 3 对比资源,保持大屏待播放和本地待播放资源一致
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void synResourceToScreen() {
        try {
            EntityWrapper<Screen> screenQuary = new EntityWrapper();
            screenQuary.eq("enable", Screen.ENABLE);
            List<Screen> screenList = screenService.selectList(screenQuary);

            for (Screen screen : screenList) {

                //查询合法的播放资源,以资源按照创建时间倒序,取出合法的数量
                EntityWrapper<Resource> entityWrapper = new EntityWrapper();
                entityWrapper.eq("no", screen.getNo());
                entityWrapper.eq("enable", Resource.ENABLE);
                entityWrapper.eq("delFlag", Resource.UNDEL);
                entityWrapper.orderBy("createDate", false);//倒序
                List<Resource> resourceList = resourceService.selectList(entityWrapper);

                List<Resource> picResourceList = resourceList.stream().filter(meta ->
                        Resource.TYPE_PIC.equals(meta.getType())
                ).collect(Collectors.toList());

                //应该显示的合法资源
                List<Resource> legalList = new ArrayList();
                if (resourceList != null && picResourceList.size() > screen.getPlayPicNum()) {
                    legalList.addAll(resourceList.subList(0, screen.getPlayPicNum()));
                } else {
                    legalList.addAll(resourceList);
                }
                List<VsnPlay> vsnPlayList = msgService.getRemoteScreenPlayList(screen);
                List<String> vsnNameList = vsnPlayList.stream().map(VsnPlay::getName).collect(Collectors.toList());
                List<String> legalNameList = legalList.stream().map(Resource::getVsnName).collect(Collectors.toList());

                //更新合法的资源
                legalList.forEach(legalResource -> {
                    if (vsnNameList.contains(legalResource.getVsnName())) {
                        return;
                    } else {//上传资源
                        msgService.putResource(screen.getUri(), legalResource);
                        log.info("定时上传显示资源 screenNo:{} url:{}", screen.getNo(), screen.getUri() + legalResource.getVsnName());
                    }
                });

                //删除过期的资源(只针对FTP 图片)
                vsnPlayList.forEach(vsnPlay -> {
                    if (!legalNameList.contains(vsnPlay.getName())) {
                        msgService.putDownResource(screen.getUri(), vsnPlay.getName());
                        log.info("定时删除过期资源 screenNo:{} url:{}", screen.getNo(), screen.getUri() + vsnPlay.getName());
                    }
                });

            }
        } catch (Exception e) {
            log.error("定时同步大屏<-->数据库资源异常:{}", e);
        }


    }

    /**
     * 转换文件名
     *
     * @param screenList
     * @param originFileName
     * @return
     */
    public Resource convertFileName(List<Screen> screenList, FTPFile ftpFile, Resource resource) {
        String todayStamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        String originFileName = ftpFile.getName();

        for (Screen screen : screenList) {

            //根据匹配规则,获取新文件名
            if (validatePicOfScreen(originFileName,screen.getRegexChar())) {

                //新文件名 大屏编号 + 日期 + 权重
                String prifixName = screen.getNo() + "_" + todayStamp;
                String picSuffixName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
                String newFileName = prifixName + picSuffixName;
                resource.setNo(screen.getNo());
                resource.setCreateDate(new Date());
                resource.setFileName(newFileName);
                resource.setFilePath(constantConfig.fileCache);
                resource.setOriginName(originFileName);
                resource.setVsnName(prifixName + ".vsn");
                resource.setEnable(Resource.ENABLE);//默认设置可用
                resource.setDelFlag(Resource.UNDEL);//默认没有删除
                resource.setType(Resource.TYPE_PIC);//默认设置为违法、路况、停车图类,固定播放张数

                //ftpFile.getTimestamp().getTimeZone().getOffset(0); utc 时间加上时差
                Date date = new Date(ftpFile.getTimestamp().getTimeInMillis());
                String ftpResourceDate = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
                resource.setResourceDateTime(ftpResourceDate);
            }
        }
        return resource;
    }

    /**
     * 判断图片是否符合大屏规则
     * @param originFileName
     * @param regexChar
     * @return
     */
    public boolean validatePicOfScreen(String originFileName,String regexChar) {
        if (StringUtils.isBlank(regexChar)) {
            return false;
        }
        String[] regexArray = regexChar.split(",");
        for (String regex : regexArray) {
            if (originFileName.indexOf(regex) != -1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 生成图片
     *
     * @throws Exception
     */
    //@Scheduled(cron = "*/10 * * * * ?")
    public void sendMessageToScreen() throws Exception {
        try {
            log.info("scanPic...");

            String text = "测试文字测试文字" +
                    "\r\n测试测试文字测试文字测试";
            System.out.println(text);

            //WaterMarkUtils.createWaterMark(constantConfig.backGroundPic, "F:/target.png", text);
            /*FTPFile[] files = ftpUtils.getFiles(".");
            for (FTPFile file : files) {
                log.info("file name : {}",file.getName());

                ftpUtils.downFile(file.getName(),"f:/"+file.getName());

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
