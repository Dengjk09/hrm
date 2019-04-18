package com.dengjk.system.service.impl;

import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.system.BsRole;
import com.dengjk.system.BsUser;
import com.dengjk.system.dao.RoleRepository;
import com.dengjk.system.dao.UserRepository;
import com.dengjk.system.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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


    /**
     * @param dataMap
     * @return
     */
    @Override
    public Result assignRoles(Map<String, Object> dataMap) {
        String userId = (String) dataMap.get("userId");
        List<String> roles = (List<String>) dataMap.get("roleIds");
        /**根据id查出用户信息*/
        BsUser bsUser = userRepository.findById(userId).get();
        Set<BsRole> rolesDb = bsUser.getRoles();
        Set<BsRole> byIdIn = roleRepository.findByIdIn(roles);
        /**把角色设置到用户*/
        bsUser.setRoles(byIdIn);
        userRepository.save(bsUser);
        return ResultUtil.success("保存成功");
    }
}
