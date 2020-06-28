package com.tacbin.town.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tacbin.town.data.entity.Product;

import java.util.List;

@Mapper
public interface IProductMapper extends BaseMapper<Product> {

    List<Product> selectPrdByParentCatIdUserId(@Param(value = "parentCatId") long parentCatId, @Param(value = "userId") long userId);
}
