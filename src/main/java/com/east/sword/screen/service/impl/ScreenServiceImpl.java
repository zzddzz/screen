package com.east.sword.screen.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.mapper.ScreenMapper;
import com.east.sword.screen.service.IScreenService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * screen service
 *
 * @author ZZD
 * @since 2020-02-19
 */
@Service
public class ScreenServiceImpl extends ServiceImpl<ScreenMapper, Screen> implements IScreenService {

    @Override
    public List<Screen> selectPageInfo(Page page, Screen screen) {
        return baseMapper.selectPageInfo(page,screen);
    }
}
