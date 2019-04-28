package com.dengjk.system.controller;

import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dengjk
 * @create 2019-04-28 18:44
 * @desc 好像shiro的登入失败跳转不能直接跳类上面的路径 @RequestMapping("/sys/user/")
 **/

@RestController
@Api(value = "shiro登入失败页面", description = "操作用户desc")
public class ErrorController {

    @GetMapping(value = "/loginError")
    @ApiOperation("模拟shiro登入页面和登入失败页面,用于shiro登入操作")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result loginError(Integer code) {
        if (code == 1) {
            return ResultUtil.authFail("未登入,请登入");
        } else {
            return ResultUtil.authFail("未授权,联系管理员");
        }
    }

}
