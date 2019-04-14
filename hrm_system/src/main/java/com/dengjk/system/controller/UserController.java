package com.dengjk.system.controller;

import com.dengjk.common.utils.Result;
import com.dengjk.system.BsUser;
import com.dengjk.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dengjk
 * @create 2019-04-14 19:19
 * @desc 操作用户控制层
 **/
@RestController
@RequestMapping("/sys/user")
@Api(value = "操作用户", description = "操作用户desc")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    @ApiOperation("添加用户信息")
    private Result addUser(@RequestBody BsUser bsUser) {
        return userService.addUser(bsUser);
    }


    @GetMapping("/findUserById/{id}")
    @ApiOperation("根据id查询用户")
    public Result findUserById(@PathVariable(value = "id") Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/deleteUserById/{id}")
    @ApiOperation("根据id删除用户")
    public Result deleteUserById(@PathVariable(value = "id")Long id){
        return userService.deleteUserById(id);
    }
}
