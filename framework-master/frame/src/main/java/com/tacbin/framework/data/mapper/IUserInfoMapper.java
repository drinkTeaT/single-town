package com.tacbin.framework.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.tacbin.framework.base.permission.data.entity.UserInfo;

@Mapper
public interface IUserInfoMapper extends BaseMapper<UserInfo> {
}