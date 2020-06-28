package com.tacbin.framework.base.permission.operation;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.permission.config.realm.MyShiroRealm;
import com.tacbin.framework.base.permission.data.entity.UserInfo;
import com.tacbin.framework.base.utils.UserInfoBeanUtil;
import com.tacbin.framework.data.Iservice.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Login {
    @Autowired
    private IUserInfoService IUserInfoService;

    @Autowired
    private UserInfoBeanUtil userInfoBeanUtil;

    @Autowired
    private MyShiroRealm shiroRealm;

    /**
     * 用户注册
     */
    public ResponseInfo userSingUp(UserInfo info) {
        String salt = userInfoBeanUtil.genSalt();
        info.setPassword(userInfoBeanUtil.decodePwd(info, salt));
        info.setSalt(salt);
        return IUserInfoService.saveNewUser(info);
    }

    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 密码
     */
    public ResponseInfo userLoginIn(String name, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        Subject subject = getSubject();
        try {
            subject.login(token);
            // 会话管理
            sessionManager(subject);
        } catch (Exception e) {
            // 身份验证失败
            log.error(name + " 身份认证失败 ");
            return ResponseInfo.builder().message("登录失败,账号或密码错误").status(Status.FAIL).build();
        }
        return ResponseInfo.builder().message("登录成功").status(Status.SUCCESS).build();
    }

    /**
     * 登出
     */
    public ResponseInfo userLogOut() {
        Subject subject = getSubject();
        subject.logout();
        return ResponseInfo.builder().message("登出成功").status(Status.SUCCESS).build();
    }

    /**
     * 是否登录
     */
    public ResponseInfo checkLoginStatus() {
        Subject subject = getSubject();
        boolean hasLogin = subject.isAuthenticated();
        return hasLogin ? ResponseInfo.builder().message("已登录").status(Status.SUCCESS).build() :
                ResponseInfo.builder().message("未登录").status(Status.FAIL).build();
    }

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 会话管理
     *
     * @param subject
     */
    private void sessionManager(Subject subject) {
        UserInfo user = (UserInfo) subject.getPrincipal();
        Session session = subject.getSession();
        // 会话ID
        session.getId();
        // 访问者的主机
        if (StringUtils.isEmpty(session.getHost())) {
            String loginIp = session.getHost();
            user.setLoginIp(loginIp);
            IUserInfoService.updateUserById(user);
        }
        // 最近访问
        session.getLastAccessTime();
        // 设置半小时超时
        session.setTimeout(1000 * 60 * 30);
    }

    /**
     * 修改密码
     */
    public void changePassword(String newPassword, String repeatPassword) throws Exception {
        if (StringUtils.isEmpty(newPassword)) {
            throw new Exception("新密码不能为空或者不能为空格");
        }
        if (!newPassword.equals(repeatPassword)) {
            throw new Exception("两次密码输入不一致");
        }
        UserInfo info = userInfoBeanUtil.getCurrentUser();
        String salt = userInfoBeanUtil.genSalt();
        info.setPassword(newPassword);
        info.setPassword(userInfoBeanUtil.decodePwd(newPassword, salt));
        info.setSalt(salt);
        IUserInfoService.updateUserById(info);
        // 登出并清除缓存
        userLogOut();
        Cache<Object, AuthenticationInfo> cache = shiroRealm.getAuthenticationCache();
        if (cache != null) {
            cache.remove(info.getUserName());
        }
    }
}
