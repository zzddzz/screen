package com.east.sword.screen.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.east.sword.screen.entity.Screen;

import java.util.List;

/**
 * screen service
 *
 * @author ZZD
 * @since 2020-02-19
 */
public interface IScreenService extends IService<Screen> {

    List<Screen> selectPageInfo(Page page, Screen screen);

}
