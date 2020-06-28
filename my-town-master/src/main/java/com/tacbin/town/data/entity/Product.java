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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description :商品表
 * @Author : Administrator
 * @Date : 2020-03-01 11:47
 **/
@TableName("products")
@Getter
@Setter
public class Product implements Serializable {
    @TableId("ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private long id = genId();

    @TableField("USERID")
    @JsonSerialize(using = ToStringSerializer.class)
    private long userID;

    @TableField("CATEGORY_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private long categoryId;

    @TableField("VIEW_COUNT")
    private int viewCount;

    @TableField("PRICE")
    private BigDecimal price;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("IMG1")
    private String img1;

    @TableField("IMG2")
    private String img2;

    @TableField("IMG3")
    private String img3;

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
