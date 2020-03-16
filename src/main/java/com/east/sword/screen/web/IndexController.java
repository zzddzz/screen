package com.east.sword.screen.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.entity.SysUser;
import com.east.sword.screen.service.IScreenService;
import com.east.sword.screen.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 首页
 * @CreateDate 23:05 2020/2/21.
 * @Author ZZD
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private IScreenService screenService;

    @Autowired
    private ISysUserService userService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public String validateLogin(String name, String password, HttpSession httpSession) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("name",name);
        entityWrapper.eq("password",password);

        List<SysUser> sysUsers = userService.selectList(entityWrapper);
        if (null != sysUsers && sysUsers.size() > 0 ) {
            httpSession.setAttribute("isLogin","yes");
            return SUCCESS;
        } else {
            return FAIL;
        }
    }

    @GetMapping("logOut")
    public String logOut(HttpSession httpSession) {
        httpSession.removeAttribute("isLogin");
        return "/";
    }

    @GetMapping("/index")
    public String index(Model model) {
        EntityWrapper entityWrapper = new EntityWrapper();
        List<Screen> screenList = screenService.selectList(entityWrapper);
        long enableNum = screenList.stream().map(Screen::getEnable).count();
        long unable = screenList.size() - enableNum;
        model.addAttribute("total",screenList.size());
        model.addAttribute("enable",enableNum);
        model.addAttribute("unable",unable);
        return "screen";
    }
}
