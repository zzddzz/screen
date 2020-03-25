package com.east.sword.screen.msg;

import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.vo.VsnPlay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息路由
 *
 * @CreateDate 14:18 2020/3/25.
 * @Author ZZD
 */
@Slf4j
@Service(("routerMsgService"))
public class MsgRouterImpl implements IMsgService {

    @Qualifier("kltMsgService")
    @Autowired
    private IMsgService kltMsgService;

    @Qualifier("jxMsgService")
    @Autowired
    private IMsgService jxMsgService;

    @Override
    public void sendMsg(Screen screen) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.sendMsg(screen);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {
            jxMsgService.sendMsg(screen);
        }

    }

    @Override
    public List<VsnPlay> getRemoteScreenPlayList(Screen screen) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.getRemoteScreenPlayList(screen);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
        return null;
    }

    @Override
    public void putDownResource(Screen screen, Resource resource) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.putDownResource(screen, resource);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
    }

    @Override
    public void delResource(Screen screen, Resource resource) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.delResource(screen, resource);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
    }

    @Override
    public void putResource(Screen screen, Resource resource) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.putResource(screen, resource);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
    }

    @Override
    public void sleep(Screen screen) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.sleep(screen);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
    }

    @Override
    public void wakeUp(Screen screen) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.wakeUp(screen);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
    }

    @Override
    public void reboot(Screen screen) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {
            kltMsgService.reboot(screen);
        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
    }

    @Override
    public void changeLight(Screen screen, Resource resource) {
        if (Screen.TYPE_KLT.equals(screen.getType())) {

        }
        if (Screen.TYPE_JX.equals(screen.getType())) {

        }
    }
}
