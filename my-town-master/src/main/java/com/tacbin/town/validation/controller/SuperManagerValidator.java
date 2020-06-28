package com.tacbin.town.validation.controller;

import com.tacbin.framework.base.permission.data.entity.UserInfo;
import com.tacbin.framework.data.Iservice.IUserInfoService;
import com.tacbin.town.data.Iservice.IUserExtendService;
import com.tacbin.town.data.entity.UserExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description :三高权限检验器
 * @Author : Administrator
 * @Date : 2020-03-18 9:50
 **/
@Component
public class SuperManagerValidator {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserExtendService userExtendService;

    /**
     * 登录前校验
     */
    public void beforeLoginInValidation(String userName, String password) throws Exception {
        // 校验账号是否异常
        checkExceptionAccount(userName);
    }

    private void checkExceptionAccount(String userName) throws Exception {
        UserExtend userExtend = userExtendService.queryExeInfoByUserName(userName);
        if (userExtend == null) {
            throw new Exception("账号异常");// 按道理来讲，新建的账号都会有userExtend
        } else if (userExtend.getExpireTime().getTime() < new Date().getTime()) {
            throw new Exception("账号过期");
        } else {
            return;
        }
    }
}
