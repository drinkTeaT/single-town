package com.tacbin.town.controller.customer;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.town.data.Iservice.ICategoryService;
import com.tacbin.town.data.Iservice.IProductService;
import com.tacbin.town.data.Iservice.IUserExtendService;
import com.tacbin.town.data.entity.Category;
import com.tacbin.town.data.entity.UserExtend;
import com.tacbin.town.validation.controller.CustomerVisitValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description :买家查看商品,不需要登录
 * @Author : Administrator
 * @Date : 2020-03-15 13:44
 **/
@RestController
@RequestMapping("/customer/")
public class CustomerVisitController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IUserExtendService userExtendService;
    @Autowired
    private CustomerVisitValidator customerVisitValidator;

    @RequestMapping(method = RequestMethod.GET, value = "/visitProducts")
    @ApiOperation(value = "根据一级类目及用户ID查看下方商品", notes = "父类目id")
    public ResponseInfo<List<Category>> viewPrdByCatIdAndUserId(@RequestParam Long catId, @RequestParam Long userId) {
        List<Category> products = productService.viewPrdByCatIdAndUserId(catId, userId);
        return new ResponseInfo<>("查询成功", Status.SUCCESS, products);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/visitCategories")
    @ApiOperation(value = "根据商家id查看一级目录", notes = "目录")
    public ResponseInfo<List<Category>> queryFirstCat(@RequestParam Long userId) throws Exception {
        customerVisitValidator.validateBeforeQueryFirstCat(userId);
        List<Category> categoryList = categoryService.queryFirstCatsRemoveEmptyCategory(userId);
        return new ResponseInfo("查询成功", Status.SUCCESS, categoryList);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/queryExeInfo")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    public ResponseInfo queryExeInfoByUserId(Long userId) {
        UserExtend userExtend = userExtendService.queryExeInfoByUserId(userId);
        return new ResponseInfo("查询成功", Status.SUCCESS, userExtend);
    }
}
