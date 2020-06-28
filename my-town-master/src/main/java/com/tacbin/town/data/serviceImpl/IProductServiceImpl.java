package com.tacbin.town.data.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.framework.base.utils.UserInfoBeanUtil;
import com.tacbin.town.common.Constants;
import com.tacbin.town.data.Iservice.IProductService;
import com.tacbin.town.data.entity.Category;
import com.tacbin.town.data.entity.Product;
import com.tacbin.town.data.mapper.ICategoryMapper;
import com.tacbin.town.data.mapper.IProductMapper;
import com.tacbin.town.tools.CategoryCountTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:23
 **/
@Service
public class IProductServiceImpl implements IProductService {
    @Autowired
    private IProductMapper productMapper;
    @Autowired
    private UserInfoBeanUtil userInfoUtil;
    @Autowired
    private ICategoryMapper categoryMapper;
    @Autowired
    private com.tacbin.town.data.Iservice.ICategoryService ICategoryService;
    @Autowired
    private CategoryCountTool categoryCountTool;

    /**
     * 添加产品，生成主键以及存储将数量存储到redis中
     *
     * @param product
     */
    public void addOne(Product product) {
        product.setUserID(userInfoUtil.getCurrentUser().getId());
        productMapper.insert(product);
        // 存储到redis如果新增为可用
        long firstId = categoryMapper.selectById(product.getCategoryId()).getParentId();
        String key = Constants.PRODUCT_REDIS + userInfoUtil.getCurrentUser().getUserName() + ":" + firstId;
        if ("1".equals(product.getEnable())) {
            categoryCountTool.addEnablePrdNumber(key);
        }
    }

    public void delOne(Long productId) {
        // redis数量减1，获取商品一级目录id
        Product product = productMapper.selectById(productId);
        long firstId = categoryMapper.selectById(product.getCategoryId()).getParentId();
        String key = Constants.PRODUCT_REDIS + userInfoUtil.getCurrentUser().getUserName() + ":" + firstId;
        categoryCountTool.removeEnablePrdNumber(key);
        // 删除放最后
        productMapper.deleteById(productId);
    }

    public Product viewOne(Long productId) {
        return productMapper.selectById(productId);
    }

    /**
     * 找出子目录下的所有商品
     */
    public List<Product> batchSelectInCategories(List<Long> catIds) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.lambda().in(Product::getCategoryId, catIds);
        return productMapper.selectList(productQueryWrapper);
    }

    public void editOne(Product prd, Product product) {
        // 如果不可用减一,并且修改前为可用
        long firstId = categoryMapper.selectById(prd.getCategoryId()).getParentId();
        String key = Constants.PRODUCT_REDIS + userInfoUtil.getCurrentUser().getUserName() + ":" + firstId;
        if (!"1".equals(product.getEnable()) && "1".equals(prd.getEnable())) {
            categoryCountTool.removeEnablePrdNumber(key);
        }
        if ("1".equals(product.getEnable()) && !"1".equals(prd.getEnable())) {
            categoryCountTool.addEnablePrdNumber(key);
        }
        setProductValueByEdit(prd, product);
        productMapper.updateById(prd);
    }

    /**
     * 根据父类目以及用户ID查看商品
     */
    public List<Product> viewPrdByParentCatId(Long parentCatId) {
        return productMapper.selectPrdByParentCatIdUserId(parentCatId, userInfoUtil.getCurrentUser().getId());
    }

    public List<Category> viewPrdByCatIdAndUserId(Long catId) {
        // 查出所有二级目录
        List<Category> categories = ICategoryService.querySecondCats(catId);
        List<Long> catIds = categories.stream().map(Category::getId).collect(Collectors.toList());
        if (catIds.size() == 0) {
            return categories;
        }
        // 根据二级目录查询下面的商品
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.lambda().in(Product::getCategoryId, catIds).eq(Product::getUserID, userInfoUtil.getCurrentUser().getId());
        List<Product> products = productMapper.selectList(productQueryWrapper);
        // 如果enable为1，设置为已公开，否则为已隐藏
        products.forEach(x -> {
            if ("1".equals(x.getEnable())) {
                x.setEnable("已公开");
            } else {
                x.setEnable("已隐藏");
            }
        });
        // 将商品挂在二级目录下
        Map<Long, List<Product>> map = products.stream().collect(Collectors.groupingBy(Product::getCategoryId));
        categories.forEach(x -> {
            x.setProductList(map.get(x.getId()));
        });
        return categories;
    }

    public void saveOrAddById(Product product) {
        if (product.getId() == 0) {// 新增
            long userId = userInfoUtil.getCurrentUser().getId();
            Product prd = new Product();
            prd.setUserID(userId);
            setProductValueByAdd(prd, product);
            addOne(prd);
        } else {
            // 查到该条数据
            Product prd = viewOne(product.getId());
            editOne(prd, product);
        }
    }

    @Override
    public List<Category> viewPrdByCatIdAndUserId(Long catId, Long userId) {
        // 查出所有二级目录
        List<Category> categories = ICategoryService.querySecondCats(catId);
        List<Long> catIds = categories.stream().map(Category::getId).collect(Collectors.toList());
        if (catIds.size() == 0) {
            return categories;
        }
        // 根据二级目录查询下面的商品
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.lambda().in(Product::getCategoryId, catIds).eq(Product::getUserID, userId);
        List<Product> products = productMapper.selectList(productQueryWrapper);
        // 已隐藏的商品不做展示
        products.removeIf(x -> !"1".equals(x.getEnable()));
        // 将商品挂在二级目录下
        Map<Long, List<Product>> map = products.stream().collect(Collectors.groupingBy(Product::getCategoryId));
        // 如果二级目录下没有商品则去掉二级目录
        categories.removeIf(x -> map.get(x.getId()) == null);
        categories.forEach(x -> {
            x.setProductList(map.get(x.getId()));
        });
        return categories;
    }

    private void setProductValueByEdit(Product prd, Product product) {
        if (!StringUtils.isEmpty(product.getDescription())) {
            prd.setDescription(product.getDescription());
        }
        if (!StringUtils.isEmpty(product.getImg1())) {
            prd.setImg1(product.getImg1());
        }
        if (product.getPrice() != null) {
            prd.setPrice(product.getPrice());
        }
        prd.setEnable(product.getEnable());
    }

    private void setProductValueByAdd(Product prd, Product product) {
        prd.setDescription(product.getDescription());
        prd.setImg1(product.getImg1());
        prd.setPrice(product.getPrice());
        prd.setEnable(product.getEnable());
        prd.setCategoryId(product.getCategoryId());
    }
}
