package com.tacbin.town.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tacbin.town.data.entity.UserExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-13 18:42
 **/
@Mapper
public interface UserExtendMapper extends BaseMapper<UserExtend> {
    UserExtend queryExtendByUserName(@Param(value = "userName") String userName);
}
