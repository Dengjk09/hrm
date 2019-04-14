package com.dengjk.system.service;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsPermission; /**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
public interface PermissionService {
    Result addPermission(BsPermission bsPermission);

    Result findPermissionById(String id);

    Result deletePermissionById(String id);
}
