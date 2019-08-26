package com.dengjk.system.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dengjk
 * @create 2019-08-22 22:58
 * @desc security包是用于模拟spring-security权限控制场景
 **/
@Controller
@RequestMapping("/sys/security/")
@Api(value = "spring-security操作", description = "security操作")
public class SecurityContoller {


    @GetMapping("/login")
    @ApiOperation("模拟spring-security登入")
    public String login() {
        return "login";
    }

    @PostMapping("/loginSuccess")
    public String loginSuccess() {
        return "index";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess2() {
        return "index";
    }


    @GetMapping("/userInfo")
    @ApiOperation("获取已经登入的用户信息")
    @ResponseBody
    public Object getUserInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }


    /**
     * 登入之后的模拟跳转页面
     *
     * @return
     */
    @ApiOperation("查询订单")
    @RequestMapping("/showOrder")
    public String showOrder() {
        return "showOrder";
    }

    @ApiOperation("添加订单")
    @RequestMapping("/addOrder")
    public String addOrder() {
        return "addOrder";
    }

    @ApiOperation("修改订单")
    @RequestMapping("/updateOrder")
    public String updateOrder() {
        return "updateOrder";
    }

    @ApiOperation("删除订单")
    @RequestMapping("/deleteOrder")
    @PreAuthorize("sys_user_manage")
    public String deleteOrder() {
        return "deleteOrder";
    }
}
