package com.tacbin.town.data.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Role;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.data.entity.UserInfo;
import com.tacbin.framework.base.permission.operation.Login;
import com.tacbin.framework.base.utils.UserInfoBeanUtil;
import com.tacbin.town.data.Iservice.IUserExtendService;
import com.tacbin.town.data.entity.UserExtend;
import com.tacbin.town.data.mapper.UserExtendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:24
 **/
@Service
public class IUserExtendServiceImpl implements IUserExtendService {
    @Autowired
    private UserInfoBeanUtil userInfoBeanUtil;
    @Autowired
    private UserExtendMapper extendMapper;
    @Autowired
    private Login login;

    /**
     * 更新或新增用户信息
     */
    @Override
    public void addOrUpdateInfo(UserExtend userExtend) throws Exception {
        long userId = userInfoBeanUtil.getCurrentUser().getId();
        if (queryExeInfoByUserId() != null) {
            // 更新
            updateSpecificFields(userExtend);
            extendMapper.updateById(userExtend);
        } else {
            // 新增
            validateShopName(userExtend.getShopName());
            userExtend.setUserId(userId);
            extendMapper.insert(userExtend);
        }
    }

    private void updateSpecificFields(UserExtend userExtend) throws Exception {
        UserExtend extend = queryExeInfoByUserId();
        // 商铺名校验
        if (!extend.getShopName().equals(userExtend.getShopName())) {
            validateShopName(userExtend.getShopName());
        }
        extend.setName(userExtend.getName());
        extend.setShopName(userExtend.getShopName());
        extend.setWxChat(userExtend.getWxChat());
        extend.setPhone(userExtend.getPhone());
        extendMapper.updateById(extend);
    }

    /**
     * 根据userID查询额外的信息
     */
    @Override
    public UserExtend queryExeInfoByUserId() {
        long userId = userInfoBeanUtil.getCurrentUser().getId();
        return queryExeInfoByUserId(userId);
    }

    /**
     * 新增正一品账号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSuperManagerAcc(String userName, Date expireTime) throws Exception {
        UserInfo info = new UserInfo();
        info.setUserName(userName);
        info.setPassword(userName);
        info.setPermissionId(Role.SUPER_MANAGER.getQueue());
        ResponseInfo info1 = login.userSingUp(info);
        if (info1.getStatus() == Status.FAIL) {
            throw new Exception(info1.getMessage());
        }
        UserExtend userExtend = new UserExtend();
        userExtend.setUserId(info.getId());
        userExtend.setExpireTime(expireTime);
        userExtend.setShopName(info.getUserName());
        extendMapper.insert(userExtend);
    }

    @Override
    public UserExtend queryExeInfoByUserId(Long userId) {
        QueryWrapper<UserExtend> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserExtend::getUserId, userId);
        return extendMapper.selectOne(wrapper);
    }

    @Override
    public UserExtend queryExeInfoByUserName(String userName) {
        return extendMapper.queryExtendByUserName(userName);
    }

    /**
     * 店铺名的唯一性
     *
     * @param shopName
     */
    private void validateShopName(String shopName) throws Exception {
        shopName = shopName.trim();
        QueryWrapper<UserExtend> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserExtend::getShopName, shopName);
        if (extendMapper.selectOne(wrapper) != null) {
            throw new Exception("存在相同的店铺名，换个试试");
        }
    }
}
