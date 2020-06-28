package com.tacbin.framework.controller;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Role;
import com.tacbin.framework.base.common.RoleConstants;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.data.entity.UserInfo;
import com.tacbin.framework.base.permission.operation.Login;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private Login login;

//    @RequestMapping(method = RequestMethod.POST, value = "/login")
//    @ApiOperation(value = "用户登录", notes = "用户登录")
//    public ResponseInfo loginIn(@RequestParam String name, @RequestParam String password) {
//        ResponseInfo responseInfo = login.userLoginIn(name, password);
//        return responseInfo;
//    }

//    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
//    @ApiOperation(value = "修改密码", notes = "修改密码")
//    public ResponseInfo changePassword(@RequestParam String newPassword, @RequestParam String repeatPassword) throws Exception {
//        login.changePassword(newPassword, repeatPassword);
//        return new ResponseInfo("修改成功", Status.SUCCESS, null);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/loginOut")
    @ApiOperation(value = "用户登出", notes = "用户登出模拟")
    public ResponseInfo loginOut() {
        return login.userLogOut();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/hasPermission")
    @ApiOperation(value = "权限测试", notes = "用户权限测试")
    public ResponseInfo hasPermission() {
        if (login.getSubject().hasRole("admin")) {
            return ResponseInfo.builder().message("有权限").status(Status.SUCCESS).build();
        } else {
            return ResponseInfo.builder().message("无权限").status(Status.FAIL).build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkLogin")
    @ApiOperation(value = "是否已登录", notes = "登录验证")
    public ResponseInfo checkLogin() {
        return login.checkLoginStatus();
    }
}
