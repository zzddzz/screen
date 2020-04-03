package com.east.sword.screen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.Screen;

import java.util.List;

/**
 * screen mapper
 *
 * @author ZZD
 * @since 2020-02-19
 */
public interface ScreenMapper extends BaseMapper<Screen> {

    List<Screen> selectPageInfo(Page page, Screen screen);

}
