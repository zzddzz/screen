package com.east.sword.screen.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 卡莱特请求地址
 * @CreateDate 13:49 2020/2/28.
 * @Author ZZD
 */
@Component
public class KltRoute {

    @Value("${klt.delete}")
    private String delRoute;

    @Value("${klt.swith}")
    private String swithRoute;

    @Value("${klt.upload}")
    private String uploadRoute;

    @Value("${klt.list}")
    private String listRoute;

    public String delRountPath(String uri,String vsnName) {
        String path = uri + delRoute.replace("{vsnName}",vsnName) ;
        return path;
    }

    public String swithRountPath(String uri,String vsnName) {
        String path = uri + swithRoute.replace("{vsnName}",vsnName) ;
        return path;
    }

    public String uploadRountPath(String uri,String vsnName) {
        String path = uri + uploadRoute.replace("{vsnName}",vsnName) ;
        return path;
    }

    public String listRountPath(String uri) {
        String path = uri + listRoute ;
        return path;
    }

}