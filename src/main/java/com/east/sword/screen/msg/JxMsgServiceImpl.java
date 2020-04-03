package com.east.sword.screen.msg;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.util.socket.SocketRouterService;
import com.east.sword.screen.vo.VsnPlay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            entityWrapper.eq("status", Resource.STATUS_ENABLE);
            List<Resource> resourcePlayList = resourceService.selectList(entityWrapper);
            List<String> playResourceName = resourcePlayList.stream().map(Resource::getVsnName).collect(Collectors.toList());
            if (null == resourcePlayList || resourcePlayList.isEmpty()) {

                //播放空内容
                Resource resource = new Resource();
                resource.setType(Resource.TYPE_FONT);
                resourcePlayList.add(resource);
            }

            //设置轮播信息
            long size = stringRedisTemplate.opsForList().size(screen.getNo().toString());
            if (size == 0 || size < resourcePlayList.size()) {
                stringRedisTemplate.delete(screen.getNo().toString());
                resourcePlayList.forEach(meta->{
                    stringRedisTemplate.opsForList().leftPush(screen.getNo().toString(),meta.getVsnName());
                });
            }

            String vsnName = stringRedisTemplate.opsForList().rightPop(screen.getNo().toString());
            EntityWrapper entityWrapperVsn = new EntityWrapper();
            entityWrapperVsn.eq("vsnName",vsnName);
            Resource resource = resourceService.selectOne(entityWrapperVsn);
            socketRouterService.sendMessage(resource, screen);

            //db 中不存在的不在播放
            if (playResourceName.indexOf(vsnName) != -1) {
                stringRedisTemplate.opsForList().leftPush(screen.getNo().toString(),resource.getVsnName());
            }


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

    @Override
    public String getPowerStatus(Screen screen) {
        return null;
    }
}
