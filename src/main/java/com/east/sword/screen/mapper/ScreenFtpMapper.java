package com.east.sword.screen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.east.sword.screen.entity.ScreenFtp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZZD
 * @since 2020-03-16
 */
public interface ScreenFtpMapper extends BaseMapper<ScreenFtp> {

    List<ScreenFtp> selectListOfScren(@Param("no") int no);

    List<ScreenFtp> selectInfoAllList(@Param("enable") String screenEnable);

}
