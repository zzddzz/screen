package com.east.sword.screen.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.FtpInfo;
import com.east.sword.screen.service.IFtpInfoService;
import com.east.sword.screen.web.dto.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/ftp")
public class FtpInfoController extends BaseController{

    @Autowired
    private IFtpInfoService ftpInfoService;

    @GetMapping("/index")
    public String index() {
        return "ftp";
    }

    @ResponseBody
    @RequestMapping("/page")
    public Map screenPageList(PageHelper<FtpInfo> pageHelper,FtpInfo ftpInfo) {
        try {
            Map data = new TreeMap();
            EntityWrapper entityWrapper = new EntityWrapper();
            if (StringUtils.isNotBlank(ftpInfo.getDesName())) {
                entityWrapper.like("des_name",ftpInfo.getDesName());
            }
            Page<FtpInfo> page = ftpInfoService.selectPage(pageHelper.getPage(),entityWrapper);
            data.put("data",page.getRecords());
            data.put("recordsTotal", page.getTotal());
            data.put("recordsFiltered", page.getTotal());
            return data;
        } catch (Exception e) {
            log.error("ftp list error:{}", e);
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/queryById")
    public FtpInfo queryById(int id) {
        FtpInfo ftpInfo = ftpInfoService.selectById(id);
        return ftpInfo;
    }

    @ResponseBody
    @RequestMapping("/resave")
    public String resave(FtpInfo ftpInfo) {
        try {
            if (ftpInfo.getId() == null) {
                ftpInfoService.insert(ftpInfo);
            } else {
                ftpInfoService.updateById(ftpInfo);
            }
            return SUCCESS;
        } catch (Exception e) {
            log.error("resave ftpinfo error :{}",e);
            return FAIL;
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String delete(int id) {
        try {
            ftpInfoService.deleteById(id);
            return SUCCESS;
        } catch (Exception e) {
            log.error("delete ftpinfo error :{}",e);
            return FAIL;
        }
    }

}

