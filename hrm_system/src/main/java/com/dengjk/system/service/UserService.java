package com.dengjk.system.service;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsUser;

import java.util.Map;

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
}
