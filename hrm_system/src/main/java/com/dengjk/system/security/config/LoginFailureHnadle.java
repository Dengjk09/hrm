package com.dengjk.system.security.config;

import com.alibaba.fastjson.JSON;
import com.dengjk.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 对登入失败的用户进行处理访问自定义格式相应,而不是跳转到登入之前的地址  要在WebSecurityConfigurerAdapter的configure中添加这个对象
 *
 * 1 .实现接口 AuthenticationFailureHandler
 * 3 .继承  spring-security实现好的一个类  SimpleUrlAuthenticationFailureHandler
 */
@Configuration
public class LoginFailureHnadle extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        /**
         * 如果要求登入失败,要响应jso数据的话需要,需要在登入的form的表单的头中加上 accept=application/json
         * 否则都会进行页面跳转
         */
        if (request.getHeader("accept") != null) {
            boolean accept = request.getHeader("accept").contains(MediaType.APPLICATION_JSON_VALUE);
            if (accept) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                logger.info(exception.getMessage());
                /**判断是密码错误*/
                if (exception instanceof BadCredentialsException) {
                    response.getWriter().write(JSON.toJSONString(ResultUtil.authFail("密码有误,请检查!!!")));
                    return;
                }
                response.getWriter().write(JSON.toJSONString(ResultUtil.authFail(exception.getMessage())));
                return;
            } else {
                super.onAuthenticationFailure(request, response, exception);
            }
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
