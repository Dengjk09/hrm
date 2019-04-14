package com.dengjk.system.service.impl;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsRole;
import com.dengjk.system.dao.RoleRepository;
import com.dengjk.system.service.RoleService;
import com.dengjk.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Result addRole(BsRole bsRole) {
        return null;
    }

    @Override
    public Result findRoleById(String id) {
        return null;
    }

    @Override
    public Result deleteRoleById(String id) {
        return null;
    }
}
