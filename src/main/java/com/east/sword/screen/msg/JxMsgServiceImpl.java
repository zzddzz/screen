package com.east.sword.screen.msg;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.util.socket.SocketRouterService;
import com.east.sword.screen.vo.VsnPlay;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 金晓通信协议实现
 *
 * @CreateDate 10:34 2020/3/18.
 * @Author ZZD
 */
@Slf4j
@Service("jxMsgService")
public class JxMsgServiceImpl implements IMsgService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private SocketRouterService socketRouterService;

    @Override
    public void sendMsg(Screen screen) {
        try {
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("no", screen.getNo());
            entityWrapper.eq("enable", Resource.STATUS_ENABLE);
            List<Resource> resourcePlayList = resourceService.selectList(entityWrapper);
            if (null == resourcePlayList || resourcePlayList.isEmpty()) {
                return;
            }

            //设置轮播信息
            String playResourceId = stringRedisTemplate.opsForValue().get(String.valueOf(screen.getNo()));
            if (StringUtils.isBlank(playResourceId)) {
                playResourceId = resourcePlayList.get(0).getId().toString();
            }
            Resource resource = resourceService.selectById(playResourceId);
            socketRouterService.sendMessage(resource, screen);

            //设置下一次金晓屏播放信息
            int nextPlayResouceIndex = 0;
            for (int i =0 ;i < resourcePlayList.size();i++){
                Resource temp = resourcePlayList.get(i);
                if (temp.getId().toString().equals(playResourceId)) {
                     nextPlayResouceIndex = (i + 1) < resourcePlayList.size() ? (i + 1) : 0;
                }
            }
            Resource nextPlayResource = resourcePlayList.get(nextPlayResouceIndex);
            stringRedisTemplate.opsForValue().set(String.valueOf(screen.getNo()),nextPlayResource.getId().toString());
        } catch (Exception e) {
            log.error("发送金晓Socket 请求异常 : {}", e.getMessage());
        }
    }

    @Override
    public List<VsnPlay> getRemoteScreenPlayList(Screen screen) {
        return null;
    }

    @Override
    public void putDownResource(Screen screen, Resource resource) {
        resource.setStatus(Resource.STATUS_UNABLE);
        resourceService.updateById(resource);
    }

    @Override
    public void delResource(Screen screen, Resource resource) {
        resource.setStatus(Resource.STATUS_ISDEL);
        resourceService.updateById(resource);
    }

    @Override
    public void putResource(Screen screen, Resource resource) {
        resource.setStatus(Resource.STATUS_ENABLE);
        resourceService.updateById(resource);
    }

    @Override
    public void sleep(Screen screen) {

    }

    @Override
    public void wakeUp(Screen screen) {

    }

    @Override
    public void reboot(Screen screen) {

    }

    @Override
    public void changeLight(Screen screen) {

    }
}
