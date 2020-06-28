package com.tacbin.town.controller.entity;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.data.entity.UserInfo;
import com.tacbin.framework.data.Iservice.IUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-16 16:13
 **/
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping(method = RequestMethod.POST, value = "/nameAndId")
    @ApiOperation(value = "用户基本信息", notes = "用户基本信息")
    public ResponseInfo getNameAndId() {
        UserInfo userInfo = userInfoService.getNameAndId();
        return new ResponseInfo("查询成功", Status.SUCCESS, userInfo);
    }
}
