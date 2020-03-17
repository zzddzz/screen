package com.east.sword.screen.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.job.msg.IMsgService;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.service.IScreenService;
import com.east.sword.screen.util.HttpClient;
import com.east.sword.screen.vo.KltRoute;
import com.east.sword.screen.vo.VsnPlay;
import com.east.sword.screen.web.dto.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * screen 控制类
 *
 * @author ZZD
 * @since 2020-02-19
 */
@Slf4j
@Controller
@RequestMapping("/screen")
public class ScreenController extends BaseController<Screen> {

    @Autowired
    private IScreenService screenService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IMsgService msgService;

    @Autowired
    private KltRoute kltRoute;

    @Autowired
    private HttpClient httpClient;

    @GetMapping("/index")
    public String loadScreenIndex() {
        return "screen";
    }

    @ResponseBody
    @RequestMapping("/list")
    public List<Screen> screenList() {
        try {
            List<Screen> screenList = screenService.selectList(entityWrapper);
            return screenList;
        } catch (Exception e) {
            log.error("screen list error:{}", e);
            return null;
        }

    }

    @ResponseBody
    @RequestMapping("/page")
    public Map screenPageList(PageHelper<Screen> pageHelper) {
        try {
            Map data = new TreeMap();
            Page<Screen> page = screenService.selectPage(pageHelper.getPage());
            data.put("data", page.getRecords());
            data.put("recordsTotal", page.getTotal());
            data.put("recordsFiltered", page.getTotal());
            return data;
        } catch (Exception e) {
            log.error("screen list error:{}", e);
            return null;
        }

    }

    @ResponseBody
    @RequestMapping("/resave")
    public String reSaveScreen(Screen screen) {
        try {
            if (screen.getNo() == null) {
                screen.setRemoteFtpPath("./");
                screenService.insert(screen);
            } else {
                screenService.updateById(screen);
            }
            return SUCCESS;
        } catch (Exception e) {
            log.error("screen resave error:{}", e);
            return FAIL;
        }
    }


    @ResponseBody
    @RequestMapping("/delete")
    public String delScreen(Screen screen) {

        try {
            screen = screenService.selectById(screen.getNo());
            EntityWrapper<Resource> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("no", screen.getNo());
            List<Resource> resourceList = resourceService.selectList(entityWrapper);
            List<String> needDelResource = resourceList.stream().map(Resource::getVsnName).collect(Collectors.toList());

            //删除大屏资源
            List<VsnPlay> vsnPlayList = msgService.getRemoteScreenPlayList(screen);
            for (VsnPlay vsnPlay : vsnPlayList) {
                if (needDelResource != null && needDelResource.contains(vsnPlay.getName())) {
                    String delUrl = kltRoute.delRountPath(screen.getUri(), vsnPlay.getName());
                    httpClient.httpDelete(delUrl);
                }
            }

            //删除DB资源
            screenService.deleteById(screen.getNo());

            return SUCCESS;
        } catch (Exception e) {
            log.error("screen delete error:{}", e);
            return FAIL;
        }
    }

    @ResponseBody
    @RequestMapping("/queryById")
    public Screen queryById(Screen screen) {
        try {
            screen = screenService.selectById(screen.getNo());
            return screen;
        } catch (Exception e) {
            log.error("screen delete error:{}", e);
            return null;
        }
    }


    //查看大屏在播图片


}

