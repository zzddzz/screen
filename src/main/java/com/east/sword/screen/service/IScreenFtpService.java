package com.east.sword.screen.service;

import com.east.sword.screen.entity.ScreenFtp;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZZD
 * @since 2020-03-16
 */
public interface IScreenFtpService extends IService<ScreenFtp> {

    List<ScreenFtp> selectListOfScren(int no);

    List<ScreenFtp> selectInfoAllList(String screenEnable);

}
