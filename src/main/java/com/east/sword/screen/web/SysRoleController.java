package com.east.sword.screen.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.SysRole;
import com.east.sword.screen.service.ISysRoleService;
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
 * @since 2020-03-16
 */
@Slf4j
@Controller
@RequestMapping("/role")
public class SysRoleController extends BaseController<SysRole>{

    @Autowired
    private ISysRoleService roleService;

    @GetMapping("/index")
    public String index() {
        return "role";
    }

    @ResponseBody
    @RequestMapping("/page")
    public Map screenPageList(PageHelper<SysRole> pageHelper) {
        try {
            Map data = new TreeMap();
            Page<SysRole> page = roleService.selectPage(pageHelper.getPage());
            data.put("data",page.getRecords());
            data.put("recordsTotal", page.getTotal());
            data.put("recordsFiltered", page.getTotal());
            return data;
        } catch (Exception e) {
            log.error("usre list error:{}", e);
            return null;
        }
    }

}

