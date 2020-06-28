package com.tacbin.town.controller.entity;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.town.data.entity.UserExtend;
import com.tacbin.town.data.Iservice.IUserExtendService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :用户其他信息控制器
 * @Author : Administrator
 * @Date : 2020-03-13 22:38
 **/
@RestController
@RequestMapping("/user/ext")
public class UserExtendController {
    @Autowired
    private IUserExtendService userExtendService;

    @RequestMapping(method = RequestMethod.POST, value = "/addOrUpdateExeInfo")
    @ApiOperation(value = "基本信息", notes = "更新用户信息")
    public ResponseInfo addOrUpdateInfo(UserExtend userExtend) throws Exception {
        userExtendService.addOrUpdateInfo(userExtend);
        return ResponseInfo.builder().message("更新成功").status(Status.SUCCESS).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/queryExeInfo")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    public ResponseInfo queryExeInfoByUserId() {
        UserExtend userExtend = userExtendService.queryExeInfoByUserId();
        return new ResponseInfo("查询成功", Status.SUCCESS, userExtend);
    }
}
