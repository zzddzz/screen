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
    void putDownResource(String uri,String vsnName);

    /**
     * 删除资源信息
     * @param uri
     * @param vsnName
     */
    void delResource(String uri,String vsnName);

    /**
     * 上传大屏资源
     * @param uri
     * @param resource
     * @return
     */
    void putResource(String uri, Resource resource);




}
