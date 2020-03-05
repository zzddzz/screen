package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Resource 大屏展示资源
 *
 * @author ZZD
 * @since 2020-02-20
 */
@Data
public class Resource extends Model<Resource> {

    public static final String ENABLE = "1";//1 资源可用, 0 资源不可用
    public static final String UNABLE = "0";

    public static final String ISDEL = "0";//1 未删除,0 已删除
    public static final String UNDEL = "1";

    public static final String TYPE_PIC = "pic";
    public static final String TYPE_FONT = "font";

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("filePath")
    private String filePath;

    @TableField("fileName")
    private String fileName;

    @TableField("vsnName")
    private String vsnName;

    @TableField("originName")
    private String originName;

    private Integer weight;
    private Integer no;

    @TableField("createDate")
    private Date createDate;

    @TableField("resourceDateTime")
    private String resourceDateTime;

    @TableField("type")
    private String type;

    @TableField("enable")
    private String enable;

    @TableField("delFlag")
    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
