package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZZD
 * @since 2020-03-07
 */
@Data
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String password;

    @TableField("role_id")
    private Integer roleId;

    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private SysRole sysRole;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_date")
    private Date createDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        ", id=" + id +
        ", name=" + name +
        ", password=" + password +
        "}";
    }
}
