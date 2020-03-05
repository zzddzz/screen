package com.east.sword.screen.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.service.IScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页
 * @CreateDate 23:05 2020/2/21.
 * @Author ZZD
 */
@Controller
public class IndexController  {

    @Autowired
    private IScreenService screenService;

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
