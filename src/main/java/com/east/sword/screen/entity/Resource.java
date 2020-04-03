package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    public static final String STATUS_ENABLE = "1";//1 资源可用, 0 资源不可用,-1 已删除
    public static final String STATUS_UNABLE = "0";
    public static final String STATUS_ISDEL = "-1";//

    public static final String TYPE_PIC = "pic";
    public static final String TYPE_FONT = "font";

    public static final String SRC_SYNC = "sync";
    public static final String SRC_CUT = "cut";

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

    @TableField(exist = false)
    private String noName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("createDate")
    private Date createDate;

    @TableField("resourceDateTime")
    private String resourceDateTime;

    @TableField("type")
    private String type;

    @TableField("status")
    private String status;


    @TableField("srcType")
    private String srcType;

    @TableField("content")
    private String content;

    @TableField(exist = false)
    private String begDate;

    @TableField(exist = false)
    private String endDate;

    @TableField("unicode")
    private String unicode;

    @TableField(exist = false)
    private String screenType;

    @TableField("size")
    private Integer size;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
