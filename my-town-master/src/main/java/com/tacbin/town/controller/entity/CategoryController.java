package com.tacbin.town.controller.entity;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.operation.Login;
import com.tacbin.town.data.entity.Category;
import com.tacbin.town.data.Iservice.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description :目录
 * @Author : Administrator
 * @Date : 2020-03-01 19:13
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private Login login;

    @RequestMapping(method = RequestMethod.GET, value = "/querySecond")
    @ApiOperation(value = "查询子目录", notes = "查询子目录")
    public ResponseInfo<List<Category>> addOne(@RequestParam long parentId) {
        List<Category> categoryList = categoryService.querySecondCats(parentId);
        return new ResponseInfo("查询成功", Status.SUCCESS, categoryList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/queryFirst")
    @ApiOperation(value = "获取一级目录及其下所有商品数量", notes = "一级目录")
    public ResponseInfo<List<Category>> queryFirstCat() {
        List<Category> categoryList = categoryService.queryFirstCats();
        return new ResponseInfo("查询成功", Status.SUCCESS, categoryList);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/view")
    @ApiOperation(value = "查看目录", notes = "查看目录")
    public void viewOne(@RequestParam List<Long> catIds) {
        categoryService.viewCats(catIds);
    }

}
