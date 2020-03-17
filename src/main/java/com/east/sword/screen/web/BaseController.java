package com.east.sword.screen.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * base controller
 * @CreateDate 17:12 2020/2/20.
 * @Author ZZD
 */
public class BaseController<T> {

    public EntityWrapper<T> entityWrapper = new EntityWrapper<>();

    public static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static final String SUCCESS = "success";

    public static final String FAIL = "fail";


}
