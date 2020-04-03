package com.east.sword.screen.util.ftp;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.FtpInfo;
import com.east.sword.screen.service.IFtpInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @CreateDate 23:58 2020/2/17.
 * @Author ZZD
 */
@Slf4j
@Component
public class FTPRouter {


    @Autowired
    public IFtpInfoService ftpInfoService;

    private static Map<String, FTPClient> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 初始化FTP客户端设置
     *
     * @return
     */
    @PostConstruct
    public void init() {
        EntityWrapper entityWrapper = new EntityWrapper();
        List<FtpInfo> ftpInfoList = ftpInfoService.selectList(entityWrapper);
        for (FtpInfo ftpInfo : ftpInfoList) {
            try {
                FTPProperties ftpProperties = new FTPProperties(ftpInfo);
                FTPClientFactory factory = new FTPClientFactory(ftpProperties);
                concurrentHashMap.put(ftpProperties.getUnicode(), factory.makeObject());
            } catch (Exception e) {
                log.error("初始化FTPClient 异常:",e);
            }
        }
        log.info("初始化FTP连接数",ftpInfoList.size());

    }


    /**
     * 获取连接对象
     *
     * @return
     * @throws Exception
     */
    public FtpClientProxy getFTPClient(String unicode) {
        FTPClient ftpClient = concurrentHashMap.get(unicode);
        try {
            ftpClient.sendNoOp();
        } catch (Exception e) {
            log.error("获取FTP 连接异常:{}",e.getMessage());
            //FTP 假死 重新加载一遍
            init();
        }
        return new FtpClientProxy(ftpClient);
    }



}
