package com.east.sword.screen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZZD
 * @since 2020-03-07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户分页
     * @param page
     * @return
     */
    List<SysUser> selectUserPage(Page page);

    SysUser selectRoleInfoById(@Param("id") int id);

}
