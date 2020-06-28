package com.tacbin.framework.data.Iservice;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.permission.data.entity.UserInfo;

public interface IUserInfoService {

    UserInfo queryUserInfoByNameAndPwd(String name, String pwd);

    UserInfo queryUserInfoByName(String name);

    ResponseInfo saveNewUser(UserInfo userInfo);

    void updateUserById(UserInfo user);

    UserInfo queryUserInfoById(long id);

    UserInfo getNameAndId();
}
