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

    @Value("${klt.power.manage}")
    private String powerManage;

    @Value("${klt.light}")
    private String lightRount;

    @Value("${klt.powerstatus}")
    private String powerStatus;

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

    public String powerManagePath(String uri) {
        String path = uri + powerManage;
        return path;
    }

    public String lightPath(String uri) {
        String path = uri + lightRount;
        return path;
    }

    public String powerStatusPath(String uri) {
        String path = uri + powerStatus;
        return path;
    }

}
