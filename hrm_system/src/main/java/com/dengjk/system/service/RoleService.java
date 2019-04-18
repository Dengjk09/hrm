package com.dengjk.system.service;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsRole;

import java.util.Map; /**
 * @author Dengjk
 * @create 2019-04-14 19:22
 * @desc
 **/
public interface RoleService {
    Result addRole(BsRole bsRole);

    Result findRoleById(String id);

    Result deleteRoleById(String id);

    Result assignPermissions(Map<String, Object> dataMap);
}
