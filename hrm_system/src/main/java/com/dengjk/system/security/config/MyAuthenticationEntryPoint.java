package com.dengjk.system.security.config;

import com.alibaba.fastjson.JSON;
import com.dengjk.common.utils.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dengjk
 * @create 2019-08-26 15:10
 * @desc AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 **/
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 决匿名用户访问无权限资源时的异常  与 AccessDeniedHandler
     *
     * @param httpServletRequest
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(ResultUtil.noAccess("您无权限,操作")));
        return;
    }
}
