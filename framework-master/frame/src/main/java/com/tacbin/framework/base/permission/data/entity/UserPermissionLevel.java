package com.tacbin.framework.base.permission.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tacbin.framework.base.common.Role;
import com.tacbin.framework.base.utils.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description :0
 * @Author : Administrator
 * @Date : 2020-03-01 12:04
 **/
@TableName("permission")
@Getter
@Setter
public class UserPermissionLevel implements Serializable {
    @TableField("LEVEL")
    private int level;

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

    @TableField("ROLE")
    private Role role;

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
