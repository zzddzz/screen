package com.east.sword.screen.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 常亮配置
 * @CreateDate 14:46 2020/2/26.
 * @Author ZZD
 */
@Slf4j
@Configuration
public class ConstantConfig implements ApplicationRunner {

    @Value("${ftp.file.cache}")
    public String fileCache;

    @Value("${file.background.path}")
    public String backGroundPic;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化配置常量================================>");
        if (StringUtils.isNotBlank(fileCache)) {
            File file = new File(fileCache);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }
    

}
