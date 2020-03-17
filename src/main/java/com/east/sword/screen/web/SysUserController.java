package com.east.sword.screen.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.SysUser;
import com.east.sword.screen.service.ISysUserService;
import com.east.sword.screen.web.dto.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZZD
 * @since 2020-03-07
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class SysUserController extends BaseController{

    @Autowired
    private ISysUserService userService;

    @GetMapping("index")
    public String loadIndex(){
        return "user";
    }

    @ResponseBody
    @RequestMapping("/page")
    public Map screenPageList(PageHelper<SysUser> pageHelper) {
        try {
            Map data = new TreeMap();
            EntityWrapper entityWrapper = new EntityWrapper();
            Page<Resource> page = userService.selectPage(pageHelper.getPage(), entityWrapper);
            data.put("data", page.getRecords());
            data.put("recordsTotal", page.getTotal());
            data.put("recordsFiltered", page.getTotal());
            return data;
        } catch (Exception e) {
            log.error("usre list error:{}", e);
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("modify")
    public String modifyUser(String oldPass,String newPass) {
        try {
            SysUser sysUser = userService.selectList(new EntityWrapper<>()).get(0);
            if (!oldPass.equals(sysUser.getPassword())) {
                return "oldFail";
            }
            sysUser.setPassword(newPass);
            userService.updateById(sysUser);
            return SUCCESS;

        } catch (Exception e) {
            log.error("修改密码异常!",e);
            return FAIL;
        }
    }

}

