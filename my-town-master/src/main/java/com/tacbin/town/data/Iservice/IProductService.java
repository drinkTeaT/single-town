package com.tacbin.town.data.Iservice;

import com.tacbin.town.data.entity.Category;
import com.tacbin.town.data.entity.Product;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 11:57
 **/
public interface IProductService {
    /**
     * 添加产品，生成主键以及存储将数量存储到redis中
     **/
    void addOne(Product product);

    void delOne(Long productId);

    Product viewOne(Long productId);

    /**
     * 找出子目录下的所有商品
     */
    List<Product> batchSelectInCategories(List<Long> catIds);

    void editOne(Product prd, Product product);

    /**
     * 根据父类目以及用户ID查看商品
     */
    List<Product> viewPrdByParentCatId(Long parentCatId);

    List<Category> viewPrdByCatIdAndUserId(Long catId);

    void saveOrAddById(Product product);

    List<Category> viewPrdByCatIdAndUserId(Long catId, Long userId);
}
