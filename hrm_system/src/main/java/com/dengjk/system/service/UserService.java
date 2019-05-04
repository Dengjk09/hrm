package com.dengjk.system.service;

import com.dengjk.common.exception.LoginErrorException;
import com.dengjk.common.utils.Result;
import com.dengjk.system.BsPermission;
import com.dengjk.system.BsUser;

import java.util.Map;
import java.util.Set;

/**
 * @author Dengjk
 * @create 2019-04-14 19:22
 * @desc
 **/
public interface UserService {

    /**
     * 添加用户
     *
     * @param bsUser
     * @return
     */
    Result addUser(BsUser bsUser);

    Result findUserById(String id);

    Result deleteUserById(String id);

    Result assignRoles(Map<String, Object> dataMap);

    Result userLogin(Map<String, Object> dataMap) throws LoginErrorException;

    Result userInfo(String authorization) throws LoginErrorException;

    Result loginByShiro(String mobile, String password) throws LoginErrorException;

    void recursionFindPerm(Set<BsPermission> bsPermissions);

    Result userInfoByShiro(String authorization);
}
