package com.tacbin.town.validation.controller;

import com.tacbin.town.data.Iservice.IUserExtendService;
import com.tacbin.town.data.entity.UserExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-18 11:02
 **/
@Component
public class CustomerVisitValidator {
    @Autowired
    private IUserExtendService userExtendService;

    /**
     * 给用户展示目录前进行校验
     */
    public void validateBeforeQueryFirstCat(Long userId) throws Exception {
        validateExpiredTime(userId);
    }

    /**
     * 日期过期校验
     */
    private void validateExpiredTime(Long userId) throws Exception {
        UserExtend userExtend = userExtendService.queryExeInfoByUserId(userId);
        if (userExtend == null) {
            throw new Exception("账号异常");// 按道理来讲，新建的账号都会有userExtend
        } else if (userExtend.getExpireTime().getTime() < new Date().getTime()) {
            throw new Exception("账号过期");
        } else {
            return;
        }
    }
}
