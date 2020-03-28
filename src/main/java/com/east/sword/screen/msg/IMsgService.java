package com.east.sword.screen.msg;

import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.vo.VsnPlay;

import java.util.List;

/**
 * @CreateDate 10:35 2020/2/25.
 * @Author ZZD
 */
public interface IMsgService {

    /**
     * 大屏轮播图片
     * @param screen
     */
    void sendMsg(Screen screen);

    /**
     * 获取大屏播放列表
     * @param screen
     * @return
     */
    List<VsnPlay> getRemoteScreenPlayList(Screen screen);

    /**
     * 下架资源信息
     * @param uri
     * @param vsnName
     */
    void putDownResource(Screen screen,Resource resource);

    /**
     * 删除资源信息
     * @param uri
     * @param vsnName
     */
    void delResource(Screen screen,Resource resource);

    /**
     * 上传大屏资源
     * @param uri
     * @param resource
     * @return
     */
    void putResource(Screen screen, Resource resource);

    /**
     * 休眠
     * @param screen
     */
    void sleep(Screen screen);

    /**
     * 唤醒
     * @param screen
     */
    void wakeUp(Screen screen);

    /**
     * 重启
     * @param screen
     */
    void reboot(Screen screen);

    /**
     * 更改亮度
     * @param screen
     * @param resource
     */
    void changeLight(Screen screen);




}
