package com.dengjk.system.controller;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsPermission;
import com.dengjk.system.service.PermissionService;
import com.dengjk.system.vo.PermissionAddVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dengjk
 * @create 2019-04-14 19:19
 * @desc 操作权限控制层
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
    @ApiOperation("添加权限信息或者修改,有id就是修改,没有id就是新增")
    private Result addPermission(@RequestBody PermissionAddVo permissionAddVo) {
        return permissionService.addOrUpdatePermission(permissionAddVo);
    }


    public Result upatePermission(@RequestBody PermissionAddVo permissionAddVo){
        return permissionService.upatePermission(permissionAddVo);
    }

    @GetMapping("/findPermissionById/{id}")
    @ApiOperation("根据id查询权限")
    public Result findPermissionById(@PathVariable(value = "id") String id) {
        return permissionService.findPermissionById(id);
    }

    @DeleteMapping("/deletePermissionById/{id}")
    @ApiOperation("根据id删除权限")
    public Result deletePermissionById(@PathVariable(value = "id")String id){
        return permissionService.deletePermissionById(id);
    }
}
