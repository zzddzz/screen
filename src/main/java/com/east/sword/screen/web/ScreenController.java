package com.east.sword.screen.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.FtpInfo;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.msg.IMsgService;
import com.east.sword.screen.service.IFtpInfoService;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.service.IScreenService;
import com.east.sword.screen.util.http.HttpClient;
import com.east.sword.screen.vo.KltRoute;
import com.east.sword.screen.vo.VsnPlay;
import com.east.sword.screen.web.dto.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Qualifier("routerMsgService")
    @Autowired
    private IMsgService msgService;

    @Autowired
    private KltRoute kltRoute;

    @Autowired
    private IFtpInfoService ftpInfoService;

    @Autowired
    private HttpClient httpClient;

    @GetMapping("/index")
    public String loadScreenIndex(Model model) {
        EntityWrapper entityWrapper = new EntityWrapper();
        List<Screen> screenList = screenService.selectList(entityWrapper);
        model.addAttribute("total", screenList.size());
        int enable = screenList.stream().filter(meta -> Screen.STATUS_ENABLE.equals(meta.getEnable())).collect(Collectors.toList()).size();
        model.addAttribute("enable", enable);
        model.addAttribute("unable", screenList.size() - enable);
        return "screen";
    }

    @GetMapping("/play-index")
    public String loadPlayIndex(Model model) {
        EntityWrapper entityWrapper = new EntityWrapper();
        List<FtpInfo> ftpInfoList = ftpInfoService.selectList(entityWrapper);
        model.addAttribute("ftpInfoList", ftpInfoList);
        return "play";
    }

    @ResponseBody
    @RequestMapping("/list")
    public List<Screen> screenList() {
        try {
            EntityWrapper entityWrapper = new EntityWrapper();
            List<Screen> screenList = screenService.selectList(entityWrapper);
            return screenList;
        } catch (Exception e) {
            log.error("screen list error:{}", e);
            return null;
        }

    }

    @ResponseBody
    @RequestMapping("/page")
    public Map screenPageList(PageHelper<Screen> pageHelper, Screen screen) {
        try {
            Map data = new TreeMap();
            EntityWrapper entityWrapper = new EntityWrapper();
            if (StringUtils.isNotBlank(screen.getEnable())) {
                entityWrapper.eq("enable", screen.getEnable());
            }
            if (StringUtils.isNotBlank(screen.getName())) {
                entityWrapper.like("name", screen.getName());
            }
            Page<Screen> page = screenService.selectPage(pageHelper.getPage(), entityWrapper);
            List<Screen> records = page.getRecords();
            records.stream().forEach(meta -> meta.setType(Screen.TYPE_INFO.get(meta.getType())));
            data.put("data", records);
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
                Screen dbScreen = screenService.selectById(screen.getNo());
                msgService.changeLight(dbScreen);
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

    @ResponseBody
    @RequestMapping("/changeStatus")
    public String changeStatus(int no, String status) {
        try {
            Screen screen = screenService.selectById(no);
            if (Screen.STATUS_ENABLE.equals(status)) {
                msgService.wakeUp(screen);
                screen.setEnable(Screen.STATUS_ENABLE);
                screenService.updateById(screen);
            }
            if (Screen.STATUS_UNABLE.equals(status)) {
                msgService.sleep(screen);
                screen.setEnable(Screen.STATUS_UNABLE);
                screenService.updateById(screen);
            }
            if (Screen.STATUS_REBOOT.equals(status)) {
                msgService.reboot(screen);
                screen.setEnable(Screen.STATUS_ENABLE);
                screenService.updateById(screen);
            }
            return SUCCESS;
        } catch (Exception e) {
            log.error("screen changeStatus error:{}", e);
            return FAIL;
        }

    }

    @ResponseBody
    @RequestMapping("/get-type-screen")
    public List<Screen> getTypeScreen(String type) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("type", type);
        List<Screen> screenList = screenService.selectList(entityWrapper);
        return screenList;
    }


}

