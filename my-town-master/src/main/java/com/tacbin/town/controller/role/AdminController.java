package com.tacbin.town.controller.role;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.RoleConstants;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.operation.Login;
import com.tacbin.town.data.Iservice.ICategoryService;
import com.tacbin.town.data.Iservice.IUserExtendService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description : 二高权限控制器
 * @Author : Administrator
 * @Date : 2020-03-14 11:21
 **/
@RestController
@RequiresRoles(value = {RoleConstants.SUPER_ADMIN, RoleConstants.ADMIN}, logical = Logical.OR)
public class AdminController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private Login login;
    @Autowired
    private IUserExtendService userExtendService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/signUp")
    @ApiOperation(value = "注册正一品", notes = "注册正一品")
    public ResponseInfo signUp(@RequestParam String userName, @RequestParam String time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expireTime = sdf.parse(time);
        userExtendService.createSuperManagerAcc(userName, expireTime);
        return new ResponseInfo("注册成功", Status.SUCCESS, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/category/addFirst")
    @ApiOperation(value = "添加一级目录", notes = "添加一级目录")
    public ResponseInfo addOne(@RequestParam String name) throws Exception {
        return categoryService.addFirstCategory(name);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/category/addSecond")
    @ApiOperation(value = "添加子目录", notes = "添加子目录")
    public ResponseInfo addOne(@RequestParam long parentId, @RequestParam String name) throws Exception {
        return categoryService.addSecondCategory(parentId, name);
    }
}
