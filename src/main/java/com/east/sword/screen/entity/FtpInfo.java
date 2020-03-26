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
@TableName("ftp_info")
public class FtpInfo extends Model<FtpInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("des_name")
    private String desName;
    private String host;
    private Integer port;
    private String name;
    private String password;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FtpInfo{" +
        ", id=" + id +
        ", desName=" + desName +
        ", name=" + name +
        ", password=" + password +
        "}";
    }
}
