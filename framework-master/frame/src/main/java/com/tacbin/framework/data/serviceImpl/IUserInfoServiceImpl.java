package com.tacbin.framework.data.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.data.entity.UserInfo;
import com.tacbin.framework.base.utils.UserInfoBeanUtil;
import com.tacbin.framework.data.Iservice.IUserInfoService;
import com.tacbin.framework.data.mapper.IUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:54
 **/
@Service
public class IUserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private IUserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoBeanUtil userInfoBeanUtil;

    public UserInfo queryUserInfoByNameAndPwd(String name, String pwd) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserName, name).eq(UserInfo::getPassword, pwd);
        return userInfoMapper.selectOne(queryWrapper);
    }

    public UserInfo queryUserInfoByName(String name) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserName, name);
        return userInfoMapper.selectOne(queryWrapper);
    }

    public ResponseInfo saveNewUser(UserInfo userInfo) {
        if (StringUtils.isEmpty(userInfo.getUserName()) || StringUtils.isEmpty(userInfo.getPassword())) {
            return ResponseInfo.builder().message("请填写账户名或者密码").status(Status.FAIL).build();
        }
        UserInfo user = queryUserInfoByName(userInfo.getUserName());
        if (user != null) {
            return ResponseInfo.builder().message("已存在该用户名").status(Status.FAIL).build();
        }
        int value = userInfoMapper.insert(userInfo);
        return value == 1 ? ResponseInfo.builder().message("注册成功").status(Status.SUCCESS).build() : ResponseInfo.builder().message("已停止注册!").status(Status.FAIL).build();
    }

    public void updateUserById(UserInfo user) {
        userInfoMapper.updateById(user);
    }

    @Override
    public UserInfo queryUserInfoById(long id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public UserInfo getNameAndId() {
        UserInfo userInfo = userInfoBeanUtil.getCurrentUser();
        UserInfo info = new UserInfo();
        info.setId(userInfo.getId());
        info.setUserName(userInfo.getUserName());
        return info;
    }

}
