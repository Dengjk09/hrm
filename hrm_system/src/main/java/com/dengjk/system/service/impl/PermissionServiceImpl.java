package com.dengjk.system.service.impl;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsPermission;
import com.dengjk.system.dao.PermissionRepository;
import com.dengjk.system.service.PermissionService;
import com.dengjk.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    public Result addPermission(BsPermission bsPermission) {
        return null;
    }


    @Override
    public Result findPermissionById(String id) {
        return null;
    }


    @Override
    public Result deletePermissionById(String id) {
        return null;
    }

}
