package com.east.sword.screen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * resource mapper
 *
 * @author ZZD
 * @since 2020-02-20
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    Date getMaxDateOfResource(@Param("no") int no);

    int getNumOfResource(@Param("originName") String originName, @Param("resourceDateTime") String resourceDateTime);

    List<Resource> selectResourcePage(Page page,Resource resource);
}
