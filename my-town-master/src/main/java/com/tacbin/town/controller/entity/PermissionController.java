package com.tacbin.town.controller.entity;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Role;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.operation.Login;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description : 权限控制器
 * @Author : Administrator
 * @Date : 2020-03-14 17:05
 **/
@RestController
@RequestMapping("/permission/")
public class PermissionController {
    @Autowired
    private Login login;

    @RequestMapping(method = RequestMethod.POST, value = "/showCard")
    @ApiOperation(value = "根据权限级别显示卡片", notes = "根据权限级别显示卡片")
    public ResponseInfo showCardByPermission() {
        boolean hasSuperAdmin = login.getSubject().hasRole(Role.SUPER_ADMIN.toString());
        boolean hasAdmin = login.getSubject().hasRole(Role.ADMIN.toString());
        if (hasSuperAdmin || hasAdmin) {
            return new ResponseInfo("全部显示", Status.SUCCESS, true);
        } else {
            return new ResponseInfo("不全部显示", Status.SUCCESS, false);
        }
    }
}
