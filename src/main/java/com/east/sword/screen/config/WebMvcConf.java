package com.east.sword.screen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @CreateDate 13:28 2020/3/7.
 * @Author ZZD
 */
@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {
    /**
     * 注入第一步定义好的拦截器
     */
    @Autowired
    private ConfigInterceptor configInterceptor;

    /**
     * 定义拦截规则, 根据自己需要进行配置拦截和排除的接口
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(configInterceptor)
                // .addPathPatterns() 是配置需要拦截的路径
                .addPathPatterns("/**")
                // .excludePathPatterns() 用于排除拦截
                .excludePathPatterns("/") // 排除127.0.0.1进入登录页
                .excludePathPatterns("/outToLogin")
                .excludePathPatterns("/assets/**") // 排除静态文件
                .excludePathPatterns("/bootstrap/**")
                .excludePathPatterns("/elementui/**")
                .excludePathPatterns("/login/**");

    }
}
