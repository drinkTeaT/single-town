package com.tacbin.town.controller.role;

import com.tacbin.framework.base.common.RoleConstants;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : 三高权限控制器,小镇商铺商家和管理员
 * @Author : Administrator
 * @Date : 2020-03-14 11:22
 **/
@RestController
@RequiresRoles({RoleConstants.SUPER_ADMIN, RoleConstants.ADMIN, RoleConstants.SUPER_MANAGER})
public class SuperManagerController {

}
