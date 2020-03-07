package com.east.sword.screen.service.impl;

import com.east.sword.screen.entity.User;
import com.east.sword.screen.mapper.UserMapper;
import com.east.sword.screen.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZZD
 * @since 2020-03-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
