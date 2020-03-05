package com.east.sword.screen.web.dto;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

/**
 * 分页参数
 *
 * @CreateDate 11:22 2020/3/1.
 * @Author ZZD
 */
@Data
public class PageHelper<T> {

    private Integer start;

    private Integer length;

    private Integer draw;

    private Integer currentPapge;

    public Page<T> getPage() {
        currentPapge = start / length + 1;
        Page<T> page = new Page<>();
        page.setCurrent(currentPapge);
        page.setSize(length);
        return page;
    }
}
