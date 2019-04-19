package com.dengjk.system.controller;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsRole;
import com.dengjk.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Dengjk
 * @create 2019-04-14 19:19
 * @desc 操作用户控制层
 * CrossOrigin解决跨域问题
 **/
@CrossOrigin
@RestController
@RequestMapping("/sys/role")
@Api(value = "操作角色", description = "操作角色")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @PostMapping("/addRole")
    @ApiOperation("添加角色信息")
    private Result addRole(@RequestBody BsRole bsRole) {
        return roleService.addRole(bsRole);
    }


    @GetMapping("/findRoleById/{id}")
    @ApiOperation("根据id查询角色")
    public Result findRoleById(@PathVariable(value = "id") String id) {
        return roleService.findRoleById(id);
    }

    @DeleteMapping("/deleteRoleById/{id}")
    @ApiOperation("根据id删除角色")
    public Result deleteRoleById(@PathVariable(value = "id") String id) {
        return roleService.deleteRoleById(id);
    }

    @PostMapping("/assignPermissions")
    @ApiOperation("给角色赋值权限,用户的roleId, 角色的id集合permIds")
    public Result assignRoles(@RequestBody Map<String, Object> dataMap) {
        return roleService.assignPermissions(dataMap);
    }
}
