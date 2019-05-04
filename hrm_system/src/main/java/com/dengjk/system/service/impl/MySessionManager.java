package com.dengjk.system.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author Dengjk
 * @create 2019-04-30 19:16
 * @desc
 **/
public class MySessionManager extends DefaultWebSessionManager {


    /**
     * 重写获取session的方法
     * 把session存放在header中
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String authorization = WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            return super.getSessionId(request, response);
        } else {
            /**去除前缀*/
            authorization = StringUtils.remove(authorization, "Bearer ");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, authorization);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return authorization;
        }
    }
}
