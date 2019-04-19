package com.dengjk.system.service.impl;

import com.dengjk.common.constants.PermissionConstants;
import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.system.BsPermission;
import com.dengjk.system.BsRole;
import com.dengjk.system.dao.PermissionRepository;
import com.dengjk.system.dao.RoleRepository;
import com.dengjk.system.service.RoleService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        roleRepository.save(bsRole);
        return ResultUtil.success("操作成功");
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
        Set<BsPermission> bsPermissions = permissionRepository.findByIdIn(permIds);
        Set<BsPermission> newPermissions = Sets.newHashSet();
        /**如果是这个权限底下有api权限,应该给赋值所有的api权限*/
        List<String> ids = bsPermissions.stream().map(x -> x.getId()).collect(Collectors.toList());
        /**通过当前权限的id 作为pid去查询这个权限下面所有的为api权限的资源*/
        Set<BsPermission> allByPidInAndType = permissionRepository.findAllByPidInAndType(ids, PermissionConstants.PY_API);
        newPermissions.addAll(bsPermissions);
        newPermissions.addAll(allByPidInAndType);
        bsRole.setPermissions(newPermissions);
        roleRepository.save(bsRole);
        return ResultUtil.success("操作成功");
    }


    /**
     * 递归添加权限的上级,直到最顶级,pid为空
     */
    public void recursionAddPerm(Set<BsPermission> newPermissions, BsPermission bsPermission) {
        if (StringUtils.isEmpty(bsPermission.getPid())) {
            newPermissions.add(bsPermission);
            return;
        }
        /**先添加当前级别的,再递归,去找它的上级*/
        newPermissions.add(bsPermission);
        BsPermission bsPermission2 = permissionRepository.findById(bsPermission.getPid()).get();
        this.recursionAddPerm(newPermissions, bsPermission2);
    }
}
