package com.east.sword.screen.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.mapper.ResourceMapper;
import com.east.sword.screen.service.IResourceService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * resource service
 *
 * @author ZZD
 * @since 2020-02-20
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Override
    public int getNumOfResource(int no) {

        EntityWrapper<Resource> entityWrapper = new EntityWrapper();
        String dateStr = DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        entityWrapper.eq("no",no).eq("createDate",dateStr);
        int num = baseMapper.selectCount(entityWrapper);
        return num;
    }

    @Override
    public Date getMaxDateOfResource(int no) {
        return baseMapper.getMaxDateOfResource(no);
    }

    @Override
    public int getNumOfResource(String originName, String resourceDateTime) {
        return baseMapper.getNumOfResource(originName,resourceDateTime);
    }

    @Override
    public List<Resource> selectResourcePage(Page page, Resource resource) {
        return baseMapper.selectResourcePage(page,resource);
    }
}
