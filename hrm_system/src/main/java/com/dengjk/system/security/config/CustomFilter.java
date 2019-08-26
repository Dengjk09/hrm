package com.dengjk.system.security.config;


import com.alibaba.fastjson.JSON;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.common.utils.SpringSecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Dengjk
 * @create 2019-08-22 22:58
 * @desc ajax 请求，如果没有登录则返回 json 信息提示。
 * <p>
 * 缺点：每次请求都会到这里,包括spring-security放开的url
 **/
//@Component
public class CustomFilter extends OncePerRequestFilter implements InitializingBean {


    /**
     * 自定义过滤器,判断用户是否已经登入,如果是没登入的话并且是restfull风格请求的话,不跳转登入页面,直接响应json数据
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currentUserLogin = SpringSecurityUtils.getCurrentUserLogin();
        /**判断是否是为restfull请求*/
        if (StringUtils.isBlank(currentUserLogin) && StringUtils.isNotBlank(request.getHeader("accept"))) {
            boolean accept = request.getHeader("accept").contains("application/json");
            if (StringUtils.isBlank(currentUserLogin) && accept) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResultUtil.authFail("请登入!!!")));
                return;
            }
        }
        /**跳转登入页面*/
        filterChain.doFilter(request, response);
    }
}
