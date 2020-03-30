package com.east.sword.screen.msg;

import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.util.http.HttpClient;
import com.east.sword.screen.vo.KltRoute;
import com.east.sword.screen.vo.VsnPlay;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 大屏发送消息
 *
 * @CreateDate 10:36 2020/2/25.
 * @Author ZZD
 */
@Slf4j
@Service("kltMsgService")
public class KltMsgServiceImpl implements IMsgService {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private KltRoute kltRoute;

    @Autowired
    private IResourceService resourceService;

    /**
     * 大屏资源轮播服务
     * 1 获取大屏资源 , 并给大屏资源排序
     * 2 根据Redis中的播放标识,播放相应资源(Redis 中没有值,默认播放第一张)
     * 3 触发大屏资源
     * 4 在Redis 中设置下一次的播放标识
     */
    @Override
    public void sendMsg(Screen screen) {
        try {

            //设置轮播图片
            String screenNo = screen.getNo().toString();
            long size = stringRedisTemplate.opsForList().size(screenNo);
            List<VsnPlay> vsnPlayList = this.getRemoteScreenPlayList(screen);
            if (size == 0) {
                vsnPlayList.stream().forEach(meta -> {
                    stringRedisTemplate.opsForList().leftPush(screenNo, meta.getName());
                });
            }
            String playVsn = stringRedisTemplate.opsForList().rightPop(screenNo);
            if (StringUtils.isNotBlank(playVsn)) {
                String putUrl = kltRoute.swithRountPath(screen.getUri(), playVsn);
                httpClient.httpPut(putUrl,null);
                log.info("调用大屏轮播资源 no:{},putUrl:{}", screen.getNo(), putUrl);
                stringRedisTemplate.opsForList().leftPush(screenNo, playVsn);
            }
        } catch (Exception e) {
            log.error("sendMsg 更新资源异常 : {}", e);
        }


    }

    /**
     * 获取远程大屏上的播放资源(设置了播放顺序)
     *
     * @param screen
     * @return
     */
    @Override
    public List<VsnPlay> getRemoteScreenPlayList(Screen screen) {
        List<VsnPlay> vsnPlayList = new ArrayList<>();
        String response = httpClient.httpGet(kltRoute.listRountPath(screen.getUri()));
        if (StringUtils.isBlank(response)) {
            return vsnPlayList;
        }
        JsonObject playList = new JsonParser().parse(response).getAsJsonObject();
        JsonArray contents = playList.getAsJsonArray("contents");
        if (contents != null && contents.size() > 0) {

            //默认取第一个节目来源 lan
            JsonObject content = contents.get(0).getAsJsonObject();
            JsonArray contentList = content.getAsJsonArray("content");
            for (int i = 0; i < contentList.size(); i++) {
                JsonElement contentMeta = contentList.get(i);
                VsnPlay vsnPlay = gson.fromJson(contentMeta, new TypeToken<VsnPlay>() {
                }.getType());
                vsnPlay.setIndex(i + 1);
                vsnPlayList.add(vsnPlay);
            }
        }
        return vsnPlayList;
    }

    /**
     * 下架资源
     *
     * @param uri
     * @param vsnName
     */
    @Override
    public void putDownResource(Screen screen, Resource resource) {
        String delUrl = kltRoute.delRountPath(screen.getUri(), resource.getVsnName());
        httpClient.httpDelete(delUrl);

        resource.setStatus(Resource.STATUS_UNABLE);
        resourceService.updateById(resource);

        stringRedisTemplate.opsForList().remove(screen.getNo().toString(),1,resource.getVsnName());
    }

    /**
     * 删除资源
     *
     * @param uri
     * @param vsnName
     */
    @Override
    public void delResource(Screen screen, Resource resource) {
        String delUrl = kltRoute.delRountPath(screen.getUri(), resource.getVsnName());
        httpClient.httpDelete(delUrl);

        resource.setStatus(Resource.STATUS_ISDEL);
        resourceService.updateById(resource);

        stringRedisTemplate.opsForList().remove(screen.getNo().toString(),1,resource.getVsnName());
    }

    /**
     * 上架资源
     *
     * @param uri
     * @param resource
     */
    @Override
    public void putResource(Screen screen, Resource resource) {
        String uploadUrl = kltRoute.uploadRountPath(screen.getUri(), resource.getVsnName());
        httpClient.httpPostMedia(uploadUrl, resource);

        resource.setStatus(Resource.STATUS_ENABLE);
        resourceService.updateById(resource);

        stringRedisTemplate.opsForList().rightPush(screen.getNo().toString(),resource.getVsnName());
    }

    /**
     * 休眠
     *
     * @param screen
     */
    @Override
    public void sleep(Screen screen) {
        String sleepUrl = kltRoute.powerManagePath(screen.getUri());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("command", "sleep");
        httpClient.httpPostJson(sleepUrl, jsonObject.toString());
    }

    /**
     * 唤醒
     *
     * @param screen
     */
    @Override
    public void wakeUp(Screen screen) {
        String sleepUrl = kltRoute.powerManagePath(screen.getUri());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("command", "wakeup");
        httpClient.httpPostJson(sleepUrl, jsonObject.toString());
    }

    /**
     * 重启
     *
     * @param screen
     */
    @Override
    public void reboot(Screen screen) {
        String sleepUrl = kltRoute.powerManagePath(screen.getUri());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("command", "reboot");
        httpClient.httpPostJson(sleepUrl, jsonObject.toString());
    }

    /**
     * 修改亮度
     *
     * @param screen
     * @param resource
     */
    @Override
    public void changeLight(Screen screen) {
        String lightUrl = kltRoute.lightPath(screen.getUri());
        JsonObject jsonObject = new JsonObject();

        //金晓的亮度值是0-255
        jsonObject.addProperty("brightness", screen.getLight());
        httpClient.httpPut(lightUrl,jsonObject.toString());
    }
}

