package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * screen
 *
 * @author ZZD
 * @since 2020-02-19
 */
@Data
public class Screen extends Model<Screen> {

    public static final String ENABLE = "1";//1 大屏可用, 0 大屏不可用
    public static final String UNABLE = "0";

    private static final long serialVersionUID = 1L;

    @TableId(value = "no", type = IdType.AUTO)
    private Integer no;
    private String name;

    @TableField("descInfo")
    private String descInfo;

    @TableField("cron")
    private String cron;

    @TableField("uri")
    private String uri;

    @TableField("remoteFtpPath")
    private String remoteFtpPath;

    @TableField("regexChar")
    private String regexChar;

    @TableField("playPicNum")
    private Integer playPicNum;

    @TableField("enable")
    private String enable;

    @Override
    protected Serializable pkVal() {
        return this.no;
    }

    @Override
    public String toString() {
        return "Screen{" +
                ", no=" + no +
                ", name=" + name +
                ", descInfo=" + descInfo +
                ", cron=" + cron +
                ", uri=" + uri +
                ", remoteFtpPath=" + remoteFtpPath +
                "}";
    }
}
