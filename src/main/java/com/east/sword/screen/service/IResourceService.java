package com.east.sword.screen.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.east.sword.screen.entity.Resource;

import java.util.Date;
import java.util.List;

/**
 * resource service
 *
 * @author ZZD
 * @since 2020-02-20
 */
public interface IResourceService extends IService<Resource> {

    int getNumOfResource(int no);

    Date getMaxDateOfResource(int no);

    int getNumOfResource(String originName,String resourceDateTime);

    List<Resource> selectResourcePage(Page page,Resource resource);
}
