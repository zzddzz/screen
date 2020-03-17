package com.east.sword.screen.service;

import com.baomidou.mybatisplus.service.IService;
import com.east.sword.screen.entity.SysMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZZD
 * @since 2020-03-16
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenu> selectList(int roleId);
}
