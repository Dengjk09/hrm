package com.dengjk.common.interceptor;

import com.dengjk.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dengjk
 * @create 2019-04-20 23:11
 * @desc jwt拦截和解析
 **/
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 进入controller之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token) && StringUtils.startsWith(token, "Bearer ")) {
            token = StringUtils.remove(token, "Bearer ");
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null) {
                /**判断这个是否有这个api的访问权限*/
                HandlerMethod methodHandle = (HandlerMethod) handler;
                /**获取方法上面controller方法上面的注解*/
                RequestMapping methodAnnotation = methodHandle.getMethodAnnotation(RequestMapping.class);
                String name = methodAnnotation.name();
                /**可以判断这个这个name是否有在用户的api权限中*/
                request.setAttribute("user_claims", claims);
                return true;
            }
        }
  /*      response.setContentType("application/json;charset=utf-8");
        response.setStatus(401);
        PrintWriter writer = response.getWriter();
        writer.write(String.valueOf(JSON.toJSONString(ResultUtil.authFail("授权失败,重定向到登录页面"))));*/
        return true;
    }
}
