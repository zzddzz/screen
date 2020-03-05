package com.east.sword.screen.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * base controller
 * @CreateDate 17:12 2020/2/20.
 * @Author ZZD
 */
public class BaseController<T> {

    public EntityWrapper<T> entityWrapper = new EntityWrapper<>();

    public static final String SUCCESS = "success";

    public static final String FAIL = "fail";


}
