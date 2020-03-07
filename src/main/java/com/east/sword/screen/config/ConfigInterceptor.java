package com.east.sword.screen.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @CreateDate 13:26 2020/3/7.
 * @Author ZZD
 */
@Slf4j
@Component
public class ConfigInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 从session中获取用户信息
        String comUser = (String) session.getAttribute("isLogin");

        // session过期
        if (!"yes".equals(comUser)) {
            log.info(">>>session过期, 跳至登录页");
            response.sendRedirect("/"); // 通过接口跳转登录页面, 注:重定向后下边的代码还会执行 ;
            return false;
        } else {
            return true;
        }
    }
}
