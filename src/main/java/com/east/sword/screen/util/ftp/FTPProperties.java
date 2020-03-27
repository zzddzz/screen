package com.east.sword.screen.util.ftp;

import com.east.sword.screen.entity.FtpInfo;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.springframework.stereotype.Component;

/**
 * @CreateDate 23:53 2020/2/17.
 * @Author ZZD
 */
@Data
@Component
public class FTPProperties {

    private String username;

    private String password;

    private String host;

    private Integer port;

    private String baseUrl = "./";//默认当前根目录

    private Integer passiveMode = FTP.BINARY_FILE_TYPE;

    private String encoding="UTF-8";

    private int clientTimeout=120000;

    private int bufferSize;

    private int transferFileType=FTP.BINARY_FILE_TYPE;

    private boolean renameUploaded;

    private int retryTime;

    private String unicode;

    public FTPProperties(){}

    public FTPProperties(FtpInfo ftpInfo) {
        this.host = ftpInfo.getHost();
        this.port = ftpInfo.getPort();
        this.username = ftpInfo.getName();
        this.password = ftpInfo.getPassword();
        this.unicode = DigestUtils.md5Hex(StringUtils.join(host,port,username,password));
    }


}
