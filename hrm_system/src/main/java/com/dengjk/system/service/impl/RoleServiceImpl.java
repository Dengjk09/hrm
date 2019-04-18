package com.dengjk.system.service.impl;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsPermission;
import com.dengjk.system.BsRole;
import com.dengjk.system.dao.PermissionRepository;
import com.dengjk.system.dao.RoleRepository;
import com.dengjk.system.service.RoleService;
import com.dengjk.system.service.UserService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;


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


    /**
     * 给角色赋值权限
     *
     * @param dataMap
     * @return
     */
    @Override
    public Result assignPermissions(Map<String, Object> dataMap) {
        String roleId = (String) dataMap.get("roleId");
        List<String> permIds = (List<String>) dataMap.get("permIds");
        BsRole bsRole = roleRepository.findById(roleId).get();
        /**权限会有一个树形关系,前段给进来的可能会是最底下层级关系的权限,这个权限所有的父级也应该赋值给这个角色*/
        Set<BsPermission> bsPermissions = permissionRepository.findByIdIn(permIds);
        Set<BsPermission> newPermissions = Sets.newHashSet();
        for (BsPermission bsPermission : bsPermissions) {
            /**第一个层级的pid应该是为空的*/
            if (StringUtils.isBlank(bsPermission.getPid())) {
                /**直接加当前级*/
                newPermissions.add(bsPermission);
                continue;
            }
            String pid = bsPermission.getPid();
            BsPermission bsPermission1 = permissionRepository.findById(pid).get();
            if(StringUtils.isNoneBlank(bsPermission1.getPid())){
                BsPermission bsPermission2 = permissionRepository.findById(bsPermission1.getPid()).get();
            }
        }
        return null;
    }
}
