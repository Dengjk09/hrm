package com.dengjk.system.security.config;

import com.alibaba.fastjson.JSON;
import com.dengjk.common.utils.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dengjk
 * @create 2019-08-26 15:02
 * @desc 自定义无权限, 响应结果
 * AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 **/
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 响应code403
     * 用来解决认证过的用户访问无权限资源时的异常  与 AuthenticationEntryPoint类似
     *
     * @param httpServletRequest
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(ResultUtil.noAccess("您无权限,操作")));
        return;
    }
}
