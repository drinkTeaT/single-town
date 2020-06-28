package com.tacbin.framework.base.permission.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import com.tacbin.framework.base.utils.SnowFlakeUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@TableName("userinfo")
@Setter
@Getter
public class UserInfo implements Serializable {
    @TableId(value = "ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private long id = genId();

    @TableField(value = "PERMISSION_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private long permissionId;

    @TableField("USER_NAME")
    private String userName;

    @TableField("PASSWORD")
    private String password;

    @TableField("FIRST_LOGINIP")
    private String loginIp;

    @TableField("SALT")
    private String salt;

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
