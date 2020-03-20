package com.east.sword.screen.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.east.sword.screen.entity.SysMenu;
import com.east.sword.screen.entity.SysRole;
import com.east.sword.screen.entity.SysUser;
import com.east.sword.screen.mapper.SysUserMapper;
import com.east.sword.screen.service.ISysMenuService;
import com.east.sword.screen.service.ISysRoleService;
import com.east.sword.screen.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZZD
 * @since 2020-03-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Override
    public SysUser getUserByName(String name) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("name",name);
        List<SysUser> sysUsers = baseMapper.selectList(entityWrapper);

        if (null != sysUsers && sysUsers.size() > 0) {
            SysUser sysUser = sysUsers.get(0);

            SysRole sysRole = sysRoleService.selectById(sysUser.getRoleId());

            if (null != sysRole) {
                List<SysMenu> sysMenus = sysMenuService.selectList(sysRole.getId());

                List<SysMenu> firstMenus = sysMenus.stream().filter(meta->0 == meta.getParentId()).collect(Collectors.toList());
                firstMenus.forEach(firstMenu->{
                    List<SysMenu> secondeMenus = new ArrayList<>();
                    sysMenus.forEach(meta->{
                        if (meta.getParentId() == firstMenu.getId()) {
                            secondeMenus.add(meta);
                        }
                    });
                    firstMenu.setSubMenu(secondeMenus);
                });

                sysRole.setSysMenus(firstMenus);
            }
            sysUser.setSysRole(sysRole);
            return sysUser;
        }
        return null;
    }

    @Override
    public List<SysUser> selectUserPage(Page page) {
        return baseMapper.selectUserPage(page);
    }
}
