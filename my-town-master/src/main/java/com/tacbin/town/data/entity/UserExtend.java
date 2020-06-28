package com.tacbin.town.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tacbin.framework.base.utils.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description :用户信息拓展表
 * @Author : Administrator
 * @Date : 2020-03-13 18:37
 **/
@TableName("user_extend")
@Getter
@Setter
public class UserExtend {
    @TableField("USERID")
    @JsonSerialize(using = ToStringSerializer.class)
    private long userId;

    @TableField("SHOP_NAME")
    private String shopName;

    @TableField("EXPIRE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    @TableField("NAME")
    private String name;

    @TableField("PHONE")
    private String phone;

    @TableField("WX_CHAT")
    private String wxChat;


    // 默认字段
    @TableId(value = "ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private long id = genId();

    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = genCreateTime();

    @TableField("MODIFY_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime = genModifyTime();

    @TableField("ENABLE")
    private String enable;

    @TableField("CREATE_ID")
    private String createId;

    @TableField("MODIFY_ID")
    private String modifyId;

    @TableField("QUEUE")
    private int queue;

    @TableField("DESCRIPTION")
    private String description;

    private Date genCreateTime() {
        if (createTime != null) {
            return createTime;
        } else {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();
        }
    }

    private Date genModifyTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private long genId() {
        if (id == 0l) {
            return SnowFlakeUtil.generateId();
        }
        return id;
    }
}
