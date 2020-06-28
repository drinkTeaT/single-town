package com.tacbin.town.data.Iservice;

import com.tacbin.town.data.entity.UserExtend;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-13 18:43
 **/
public interface IUserExtendService {
    /**
     * 更新或新增用户信息
     */
    public void addOrUpdateInfo(UserExtend userExtend) throws Exception;

    /**
     * 根据userID查询额外的信息
     */
    public UserExtend queryExeInfoByUserId();

    /**
     * 新增正一品账号
     */
    public void createSuperManagerAcc(String userName, Date expireTime) throws Exception;

    UserExtend queryExeInfoByUserId(Long userId);

    UserExtend queryExeInfoByUserName(String userName);
}
