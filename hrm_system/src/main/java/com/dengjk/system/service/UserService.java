package com.dengjk.system.service;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsUser;

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

    Result findUserById(Long id);

    Result deleteUserById(Long id);
}
