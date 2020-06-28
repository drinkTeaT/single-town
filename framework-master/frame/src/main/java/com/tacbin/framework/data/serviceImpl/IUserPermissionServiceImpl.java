package com.tacbin.framework.data.serviceImpl;

import com.tacbin.framework.base.permission.data.entity.UserPermissionLevel;
import com.tacbin.framework.data.Iservice.IUserPermissionService;
import com.tacbin.framework.data.mapper.IUserPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:54
 **/
@Service
public class IUserPermissionServiceImpl implements IUserPermissionService {
    @Autowired
    private IUserPermissionMapper userPermissionMapper;

    public UserPermissionLevel selectOneById(long id) {
        return userPermissionMapper.selectById(id);
    }
}
