package com.dengjk.system.controller;

import com.dengjk.common.exception.LoginErrorException;
import com.dengjk.common.utils.Result;
import com.dengjk.system.BsUser;
import com.dengjk.system.service.UserService;
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
@RequestMapping("/sys/user")
@Api(value = "操作用户", description = "操作用户desc")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    @ApiOperation("添加用户信息")
    private Result addUser(@RequestBody BsUser bsUser) {
        return userService.addUser(bsUser);
    }


    @GetMapping("/findUserById/{id}")
    @ApiOperation("根据id查询用户")
    public Result findUserById(@PathVariable(value = "id") String id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/deleteUserById/{id}")
    @ApiOperation("根据id删除用户")
    public Result deleteUserById(@PathVariable(value = "id") String id) {
        return userService.deleteUserById(id);
    }

    @PostMapping("/assignRoles")
    @ApiOperation("给用户分配角色,两个参数,用户的id userId , 角色的id集合 roleIds")
    public Result assignRoles(@RequestBody Map<String, Object> dataMap) {
        return userService.assignRoles(dataMap);
    }


    @PostMapping("/login")
    @ApiOperation("用户登入 mobile , password")
    public Result userLogin(@RequestBody Map<String, Object> dataMap) throws LoginErrorException {
        return userService.userLogin(dataMap);
    }


    @GetMapping(value = "/userInfo",name = "sys_user_userInfo")
    @ApiOperation("通过jwt-token获取用户信息 请求头中添加:Authorization=Bearer token")
    public Result userInfo(@RequestHeader(name = "Authorization", required = true) String authorization) throws LoginErrorException {
        return userService.userInfo(authorization);
    }
}
