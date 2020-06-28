package com.tacbin.framework.data.Iservice;

import com.tacbin.framework.base.permission.data.entity.UserPermissionLevel;
import com.tacbin.framework.data.mapper.IUserPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 12:18
 **/
public interface IUserPermissionService {
    UserPermissionLevel selectOneById(long id);
}
