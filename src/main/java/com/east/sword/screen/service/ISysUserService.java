package com.east.sword.screen.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.east.sword.screen.entity.SysUser;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZZD
 * @since 2020-03-07
 */
public interface ISysUserService extends IService<SysUser> {

    SysUser getUserByName(String name);

    List<SysUser> selectUserPage(Page page);
}
