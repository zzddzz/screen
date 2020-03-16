package com.east.sword.screen.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.service.IScreenService;
import com.east.sword.screen.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
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
@Slf4j
@Controller
public class LoginController extends BaseController {

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

        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                name,password);
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            log.error("账号密码错误",e);
            return FAIL;
        } catch (AuthorizationException e) {
            log.error("没有权限",e);
            return FAIL;
        }
        return SUCCESS;

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
