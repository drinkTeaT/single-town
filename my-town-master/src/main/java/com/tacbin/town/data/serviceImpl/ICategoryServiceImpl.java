package com.tacbin.town.data.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.utils.UserInfoBeanUtil;
import com.tacbin.framework.data.Iservice.IUserInfoService;
import com.tacbin.town.common.Constants;
import com.tacbin.town.data.Iservice.ICategoryService;
import com.tacbin.town.data.entity.Category;
import com.tacbin.town.data.entity.Product;
import com.tacbin.town.data.mapper.ICategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:23
 **/
@Service
public class ICategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryMapper categoryMapper;

    @Autowired
    private com.tacbin.town.data.Iservice.IProductService productService;

    @Autowired
    private UserInfoBeanUtil userInfoUtil;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增二级目录
     */
    public ResponseInfo addSecondCategory(long parentId, String name) throws Exception {
        validateCategoryName(name);
        Category parentCat = categoryMapper.selectById(parentId);
        if (parentCat == null || parentCat.getLevel() != 1) {// 非一级目录
            return ResponseInfo.builder().status(Status.FAIL).message("该目录无效，请重新选择目录!").build();
        }
        return addSingleCategory(parentId, name, 2);
    }

    /**
     * 新增一级目录
     */
    public ResponseInfo addFirstCategory(String name) throws Exception {
        validateCategoryName(name);
        return addSingleCategory(0, name, 1);
    }

    /**
     * 修改目录名
     */
    public void editCategory(long catId, String name) throws Exception {
        validateCategoryName(name);
        Category category = categoryMapper.selectById(catId);
        category.setName(name);
        categoryMapper.updateById(category);
    }

    public void batchDelCat(List<Long> catIds) {
        categoryMapper.deleteBatchIds(catIds);
    }

    public List<Category> viewCats(List<Long> catIds) {
        return categoryMapper.selectBatchIds(catIds);
    }

    /**
     * 添加单条目录
     */
    private ResponseInfo addSingleCategory(long parentId, String name, int level) {
        if (StringUtils.isEmpty(name)) {
            return ResponseInfo.builder().status(Status.FAIL).message("不能新增空白值").build();
        }
        Category secondCat = new Category();
        secondCat.setLevel(level);
        secondCat.setName(name);
        secondCat.setParentId(parentId);
        categoryMapper.insert(secondCat);
        return ResponseInfo.builder().status(Status.SUCCESS).message("新增成功").build();
    }

    /**
     * 获取一级目录
     */
    public List<Category> queryFirstCats() {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Category::getLevel, 1);
        List<Category> fistCats = categoryMapper.selectList(queryWrapper);
        // 通过redis获取商品数量
        String key = "";
        for (Category cat : fistCats) {
            key = Constants.PRODUCT_REDIS + userInfoUtil.getCurrentUser().getUserName() + ":" + cat.getId();
            String count = (String) redisTemplate.opsForValue().get(key);
            int num = count == null ? 0 : Integer.parseInt(count);
            cat.setCount(num);
        }
        return fistCats;
    }

    /**
     * 查询子目录
     */
    public List<Category> querySecondCats(long parentId) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Category::getParentId, parentId);
        return categoryMapper.selectList(queryWrapper);
    }

    /**
     * 批量查询子目录下的商品根据一级目录ID
     */
    public List<Product> batchQueryProductsByFirstCatId(long parentIds) {
        List<Category> subCatIds = querySecondCats(parentIds);
        // 查找商品
        List<Long> catIds = subCatIds.stream().map(Category::getId).collect(Collectors.toList());
        return productService.batchSelectInCategories(catIds);
    }

    @Override
    public List<Category> queryFirstCatsRemoveEmptyCategory(Long userId) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Category::getLevel, 1);
        List<Category> fistCats = categoryMapper.selectList(queryWrapper);
        // 通过redis获取商品数量
        String key = "";
        for (Category cat : fistCats) {
            key = Constants.PRODUCT_REDIS + userInfoService.queryUserInfoById(userId).getUserName() + ":" + cat.getId();
            String count = (String) redisTemplate.opsForValue().get(key);
            int num = count == null ? 0 : Integer.parseInt(count);
            cat.setCount(num);
        }
        fistCats.removeIf(x -> {
            return x.getCount() == 0;
        });
        return fistCats;
    }

    /**
     * 保证目录名的唯一性
     *
     * @param name
     * @throws Exception
     */
    private void validateCategoryName(String name) throws Exception {
        name = name.trim();
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getName, name);
        if (categoryMapper.selectOne(wrapper) != null) {
            throw new Exception("已有该目录名，不允许重复添加!");
        }
    }
}
