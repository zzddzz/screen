package com.east.sword.screen.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * screen
 *
 * @author ZZD
 * @since 2020-02-19
 */
@Data
public class Screen extends Model<Screen> {

    public static final String STATUS_ENABLE = "1";//1 正常, 0 休眠 ,99 重启,-1 异常
    public static final String STATUS_UNABLE = "0";
    public static final String STATUS_REBOOT = "99";
    public static final String STATUS_ERROR = "-1";


    public static Map<String,String> TYPE_INFO = new HashMap(){
        {
            put(TYPE_KLT,"卡莱特");
            put(TYPE_JX,"金晓");
        }
    };

    public static final String TYPE_KLT = "klt";//卡莱特
    public static final String TYPE_JX = "jx";//金晓


    private static final long serialVersionUID = 1L;

    @TableId(value = "no", type = IdType.AUTO)
    private Integer no;
    private String name;

    @TableField("descInfo")
    private String descInfo;

    @TableField("second")
    private Integer second;

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

    @TableField("light")
    private Integer light;

    @TableField("host")
    private String host;

    @TableField("port")
    private Integer port;

    @TableField("protocol")
    private String protocol;

    @TableField("type")
    private String type;

    @TableField(exist = false)
    private List<Integer> resourceIdList;

    public String getScheduleCron() {
        if (null != second ) {
            if (second < 60) {
                String cron = StringUtils.join("*/",second," * * * * ?");
                return cron;
            } else {
                int minute = second/60;
                int secode = second%60;

                return StringUtils.join(secode," */",minute," * * * ?");
            }
        } else {//默认20秒播放一次
            return "20/* * * * * ?";
        }
    }

    /**
     * 获取HTTP -URI
     * @return
     */
    public String getUri() {
        String uri = StringUtils.join("http://",this.getHost(),":",this.getPort());
        return uri;
    }

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
                ", uri=" + uri +
                ", remoteFtpPath=" + remoteFtpPath +
                "}";
    }
}
