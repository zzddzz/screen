package com.east.sword.screen.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置
 *
 * @CreateDate 23:05 2020/2/19.
 * @Author ZZD
 */
@Slf4j
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.info("注册分页插件...");
        PaginationInterceptor paginationInterceptor =  new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
