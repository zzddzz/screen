package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

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

    @TableField("begTime")
    private String begTime;

    @TableField("endTime")
    private String endTime;

    @TableField(exist = false)
    private String ftpName;
    @TableField(exist = false)
    private String host;
    @TableField(exist = false)
    private Integer port;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String password;

    @TableField(exist = false)
    private String unicode;

    public String getUnicode() {
        return DigestUtils.md5Hex(StringUtils.join(host,port,name,password));
    }


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
