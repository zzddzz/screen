package com.east.sword.screen.service.impl;

import com.east.sword.screen.entity.SysMenu;
import com.east.sword.screen.mapper.SysMenuMapper;
import com.east.sword.screen.service.ISysMenuService;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> selectList(int roleId) {
        return baseMapper.selectListByRole(roleId);
    }
}
