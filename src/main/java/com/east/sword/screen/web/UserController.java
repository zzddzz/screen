package com.east.sword.screen.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.User;
import com.east.sword.screen.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @GetMapping("index")
    public String loadIndex(){
        return "user";
    }

    @ResponseBody
    @RequestMapping("modify")
    public String modifyUser(String oldPass,String newPass) {
        try {
            User user = userService.selectList(new EntityWrapper<>()).get(0);
            if (!oldPass.equals(user.getPassword())) {
                return "oldFail";
            }
            user.setPassword(newPass);
            userService.updateById(user);
            return SUCCESS;

        } catch (Exception e) {
            log.error("修改密码异常!",e);
            return FAIL;
        }
    }

}

