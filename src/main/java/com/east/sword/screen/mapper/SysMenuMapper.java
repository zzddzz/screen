package com.east.sword.screen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.east.sword.screen.entity.SysMenu;
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
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectList(@Param("roleId") int roleId);
}
