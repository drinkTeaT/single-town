package com.tacbin.town.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tacbin.town.data.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 11:54
 **/
@Mapper
public interface ICategoryMapper extends BaseMapper<Category> {
}
