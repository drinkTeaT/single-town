package com.tacbin.town.controller.role;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.operation.Login;
import com.tacbin.town.validation.controller.SuperManagerValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-18 10:49
 **/
@RestController
public class NoRoleController {
    @Autowired
    private Login login;
    @Autowired
    private SuperManagerValidator superManagerValidator;

    @RequestMapping(method = RequestMethod.POST, value = "/user/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public ResponseInfo loginIn(@RequestParam String name, @RequestParam String password) throws Exception {
        // 登录前校验账号是否过期
        superManagerValidator.beforeLoginInValidation(name, password);
        ResponseInfo responseInfo = login.userLoginIn(name, password);
        return responseInfo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/changePassword")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ResponseInfo changePassword(@RequestParam String newPassword, @RequestParam String repeatPassword) throws Exception {
        login.changePassword(newPassword, repeatPassword);
        return new ResponseInfo("修改成功", Status.SUCCESS, null);
    }
}
