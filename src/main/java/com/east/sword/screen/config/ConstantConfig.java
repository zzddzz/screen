package com.east.sword.screen.config;

import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.service.IScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

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

    @Autowired
    private IScreenService screenService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化配置常量================================>");
        List<Screen> screenList = screenService.selectList(null);
        screenList.forEach(meta->{
            File file = new File(fileCache + meta.getNo() + File.separator);
            if (!file.exists()) {
                file.mkdirs();
            }
        });
    }
    

}
