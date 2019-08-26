package com.dengjk.system.security.config;

import com.alibaba.fastjson.JSON;
import com.dengjk.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 对登入成功的用户进行处理访问自定义格式相应,而不是跳转到登入之前的地址  要在WebSecurityConfigurerAdapter的configure中添加这个对象
 *
 * 1 .实现接口 AuthenticationSuccessHandler
 * 3 .继承  spring-security实现好的一个类  SavedRequestAwareAuthenticationSuccessHandler
 */
@Configuration
public class LoginSucessHnadle extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /***
     * spring-securit提供的   请求缓存  ,会把当前的请求缓存到session里面去   当登入跳转到这个requireAuth  映射上  ,会把缓存中的请求拿出来
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /**
         * 如果要求登入成功,要响应jso数据的话需要,需要在登入的form的表单的头中加上 accept=application/json
         * 否则都会进行页面跳转
         */
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (request.getHeader("accept") != null) {
            boolean accept = request.getHeader("accept").contains("application/json");
            if (accept) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.OK.value());
                logger.info("json登入成功,响应json数据");
                response.getWriter().write(JSON.toJSONString(ResultUtil.success(authentication.getPrincipal())));
                return;
            } else {
                if (savedRequest != null) {
                    String redirectUrl = savedRequest.getRedirectUrl();
                    logger.info("登入成功,自动跳转:{}", redirectUrl);
                }
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            if (savedRequest != null) {
                String redirectUrl = savedRequest.getRedirectUrl();
                logger.info("登入成功,自动跳转:{}", redirectUrl);
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
