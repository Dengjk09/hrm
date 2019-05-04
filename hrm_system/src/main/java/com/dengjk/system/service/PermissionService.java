package com.dengjk.system.service;

import com.dengjk.common.utils.Result;
import com.dengjk.system.vo.PermissionAddVo;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
public interface PermissionService {
    Result addOrUpdatePermission(PermissionAddVo permissionAddVo);

    Result findPermissionById(String id);

    Result deletePermissionById(String id);

    Result upatePermission(PermissionAddVo permissionAddVo);

    Object findPermissionDel(Integer type, String id);

    Result findPermisMenuTree();
}
