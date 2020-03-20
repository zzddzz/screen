package com.east.sword.screen.msg;

import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.vo.VsnPlay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 金晓通信协议实现
 * @CreateDate 10:34 2020/3/18.
 * @Author ZZD
 */
@Slf4j
@Service("jxMsgService")
public class JxMsgServiceImpl implements IMsgService {

    @Override
    public void sendMsg(Screen screen) {

    }

    @Override
    public List<VsnPlay> getRemoteScreenPlayList(Screen screen) {
        return null;
    }

    @Override
    public void putDownResource(String uri, String vsnName) {

    }

    @Override
    public void delResource(String uri, String vsnName) {

    }

    @Override
    public void putResource(String uri, Resource resource) {

    }
}
