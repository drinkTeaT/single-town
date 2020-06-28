package com.tacbin.town.controller.role;

import com.tacbin.framework.base.common.RoleConstants;
import com.tacbin.town.data.Iservice.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description : 最高权限控制器
 * @Author : Administrator
 * @Date : 2020-03-14 11:20
 **/
@RestController
@RequiresRoles({RoleConstants.SUPER_ADMIN})
public class SuperAdminController {
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST, value = "/category/edit")
    @ApiOperation(value = "编辑目录", notes = "编辑目录")
    public void editOne(@RequestParam long catId, @RequestParam String name) throws Exception {
        categoryService.editCategory(catId, name);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/category/batchDel")
    @ApiOperation(value = "批量删除目录", notes = "批量删除目录")
    public void batchDel(@RequestParam List<Long> catIds) {
        categoryService.batchDelCat(catIds);
    }
}
