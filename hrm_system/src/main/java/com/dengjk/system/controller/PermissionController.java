package com.dengjk.system.controller;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsPermission;
import com.dengjk.system.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dengjk
 * @create 2019-04-14 19:19
 * @desc 操作用户控制层
 * CrossOrigin解决跨域问题
 *
 **/
@CrossOrigin
@RestController
@RequestMapping("/sys/permisson")
@Api(value = "操作权限", description = "操作权限")
public class PermissionController {


    @Autowired
    private PermissionService permissionService;

    @PostMapping("/addPermission")
    @ApiOperation("添加用户信息")
    private Result addPermission(@RequestBody BsPermission bsPermission) {
        return permissionService.addPermission(bsPermission);
    }


    @GetMapping("/findPermissionById/{id}")
    @ApiOperation("根据id查询用户")
    public Result findPermissionById(@PathVariable(value = "id") String id) {
        return permissionService.findPermissionById(id);
    }

    @DeleteMapping("/deletePermissionById/{id}")
    @ApiOperation("根据id删除用户")
    public Result deletePermissionById(@PathVariable(value = "id")String id){
        return permissionService.deletePermissionById(id);
    }
}
