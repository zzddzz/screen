package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

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

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("screen_id")
    private Integer screenId;

    @TableField("ftp_id")
    private Integer ftpId;

    @TableField(exist = false)
    private String ftpName;

    @TableField("begTime")
    private String begTime;

    @TableField("endTime")
    private String endTime;


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
