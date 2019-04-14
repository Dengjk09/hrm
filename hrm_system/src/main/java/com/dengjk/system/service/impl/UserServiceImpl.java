package com.dengjk.system.service.impl;

import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.system.BsUser;
import com.dengjk.system.dao.UserRepository;
import com.dengjk.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 新增用户
     *
     * @param bsUser
     * @return
     */
    @Override
    public Result addUser(BsUser bsUser) {
        userRepository.save(bsUser);
        return ResultUtil.success("添加成功");
    }


    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public Result findUserById(String id) {
        return ResultUtil.success(userRepository.findById(id));
    }


    @Override
    public Result deleteUserById(String id) {
        userRepository.deleteById(id);
        return ResultUtil.success("删除成功");
    }
}
