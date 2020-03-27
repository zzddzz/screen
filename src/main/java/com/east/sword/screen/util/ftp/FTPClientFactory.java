package com.east.sword.screen.util.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @CreateDate 23:56 2020/2/17.
 * @Author ZZD
 */
@Slf4j
public class FTPClientFactory {

    private FTPProperties ftpProperties;

    public FTPClientFactory(FTPProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
    }

    public FTPClient makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding(ftpProperties.getEncoding());
        ftpClient.setConnectTimeout(ftpProperties.getClientTimeout());
        try {
            ftpClient.connect(ftpProperties.getHost(), ftpProperties.getPort());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.warn("FTPServer refused connection");
                return null;
            }
            boolean result = ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
            ftpClient.setFileType(ftpProperties.getTransferFileType());
            if (!result) {
                log.warn("ftpClient login failed... username is {}", ftpProperties.getUsername());
            }
        } catch (Exception e) {
            log.error("create ftp connection failed...{}", e);
            throw e;
        }

        return ftpClient;
    }


}
