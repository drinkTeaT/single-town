package com.tacbin.town.data.Iservice;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.town.data.entity.Category;
import com.tacbin.town.data.entity.Product;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 11:57
 **/
public interface ICategoryService {
    /**
     * 新增二级目录
     */
    ResponseInfo addSecondCategory(long parentId, String name) throws Exception;

    /**
     * 新增一级目录
     */
    ResponseInfo addFirstCategory(String name) throws Exception;

    /**
     * 修改目录名
     */
    void editCategory(long catId, String name) throws Exception;

    void batchDelCat(List<Long> catIds);

    List<Category> viewCats(List<Long> catIds);


    /**
     * 获取一级目录
     */
    List<Category> queryFirstCats();

    /**
     * 查询子目录
     */
    List<Category> querySecondCats(long parentId);

    /**
     * 批量查询子目录下的商品根据一级目录ID
     */
    List<Product> batchQueryProductsByFirstCatId(long parentIds);

    List<Category> queryFirstCatsRemoveEmptyCategory(Long userId);
}
