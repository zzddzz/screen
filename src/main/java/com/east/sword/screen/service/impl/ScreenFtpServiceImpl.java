package com.east.sword.screen.service.impl;

import com.east.sword.screen.entity.ScreenFtp;
import com.east.sword.screen.mapper.ScreenFtpMapper;
import com.east.sword.screen.service.IScreenFtpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZZD
 * @since 2020-03-16
 */
@Service
public class ScreenFtpServiceImpl extends ServiceImpl<ScreenFtpMapper, ScreenFtp> implements IScreenFtpService {

    @Override
    public List<ScreenFtp> selectListOfScren(int no) {
        return baseMapper.selectListOfScren(no);
    }

    @Override
    public List<ScreenFtp> selectInfoAllList(String screenEnable) {
        return baseMapper.selectInfoAllList(screenEnable);
    }
}
