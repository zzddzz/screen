package com.east.sword.screen.util.ftp;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @CreateDate 23:53 2020/2/17.
 * @Author ZZD
 */
@Data
@Component
public class FTPProperties {

    @Value("${ftp.userName}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.baseurl}")
    private String baseUrl;

    private Integer passiveMode = FTP.BINARY_FILE_TYPE;

    private String encoding="UTF-8";

    private int clientTimeout=120000;

    private int bufferSize;

    private int transferFileType=FTP.BINARY_FILE_TYPE;

    private boolean renameUploaded;

    private int retryTime;
}
