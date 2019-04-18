package com.dengjk.system.vo;

import com.dengjk.system.BsPermission;
import com.dengjk.system.PePermissionApi;
import com.dengjk.system.PePermissionMenu;
import com.dengjk.system.PePermissionPoint;
import lombok.Data;

/**
 * @author Dengjk
 * @create 2019-04-17 20:17
 * @desc 构造一个权限添加请求的视图
 **/
@Data
public class PermissionAddVo extends BsPermission {

    private PePermissionApi pePermissionApi;

    private PePermissionMenu pePermissionMenu;

    private PePermissionPoint pePermissionPoint;

}
