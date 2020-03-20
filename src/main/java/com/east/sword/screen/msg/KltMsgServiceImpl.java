package com.east.sword.screen.msg;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
            List<VsnPlay> vsnPlayList = this.getRemoteScreenPlayList(screen);
            if (vsnPlayList == null || vsnPlayList.isEmpty()) {
                return;
            }

            //设置轮播图片
            String playVsn = stringRedisTemplate.opsForValue().get(String.valueOf(screen.getNo()));
            if (StringUtils.isBlank(playVsn)) {
                playVsn = vsnPlayList.get(0).getName();
            }
            String putUrl = kltRoute.swithRountPath(screen.getUri(),playVsn);
            httpClient.httpPut(putUrl);
            log.info("调用大屏轮播资源 no:{},putUrl:{}",screen.getNo(),putUrl);

            //设置下一次的轮播图片缓存
            int justPlayIndex = 0;
            for (int i = 0; i < vsnPlayList.size(); i++) {
                VsnPlay vsnPlay = vsnPlayList.get(i);
                if (vsnPlay.getName().equals(playVsn)) {
                    justPlayIndex = i;
                }
            }
            int nextPlayIndex = justPlayIndex >= (vsnPlayList.size() - 1) ? 0 : (justPlayIndex + 1);
            stringRedisTemplate.opsForValue().set(String.valueOf(screen.getNo()),
                    vsnPlayList.get(nextPlayIndex).getName());

        } catch (Exception e) {
            log.error("更新大屏资源异常 : {}", e);
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
     * @param uri
     * @param vsnName
     */
    @Override
    public void putDownResource(String uri, String vsnName) {
        String delUrl = kltRoute.delRountPath(uri, vsnName);
        httpClient.httpDelete(delUrl);

        EntityWrapper<Resource> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("vsnName",vsnName);
        Resource resource = new Resource();
        resource.setEnable(Resource.UNABLE);
        resourceService.update(resource,entityWrapper);
    }

    /**
     * 删除资源
     * @param uri
     * @param vsnName
     */
    @Override
    public void delResource(String uri, String vsnName) {
        String delUrl = kltRoute.delRountPath(uri, vsnName);
        httpClient.httpDelete(delUrl);

        EntityWrapper<Resource> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("vsnName",vsnName);
        Resource resource = new Resource();
        resource.setEnable(Resource.UNABLE);
        resource.setDelFlag(Resource.ISDEL);
        resourceService.update(resource,entityWrapper);
    }

    /**
     * 上架资源
     * @param uri
     * @param resource
     */
    @Override
    public void putResource(String uri, Resource resource) {
        String uploadUrl = kltRoute.uploadRountPath(uri,resource.getVsnName());
        httpClient.httpPostMedia(uploadUrl, resource);
    }

    /**
     * 查询资源状态
     * @param uri
     */
    public int searchStatus(String uri) {
        String searchStatus = kltRoute.powerStatusPath(uri);
        String result = httpClient.httpGet(searchStatus);
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        return jsonObject.get("powerstatus").getAsInt();
    }
}

