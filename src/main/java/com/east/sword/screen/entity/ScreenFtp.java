package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZZD
 * @since 2020-03-16
 */
@Data
@TableName("screen_ftp")
public class ScreenFtp extends Model<ScreenFtp> {

    private static final long serialVersionUID = 1L;

    @TableId("screen_id")
    private Integer screenId;
    @TableField("ftp_id")
    private Integer ftpId;
    private Date begTime;
    private Date endTime;


    @Override
    protected Serializable pkVal() {
        return this.screenId;
    }

    @Override
    public String toString() {
        return "ScreenFtp{" +
        ", screenId=" + screenId +
        ", ftpId=" + ftpId +
        ", begTime=" + begTime +
        ", endTime=" + endTime +
        "}";
    }
}
