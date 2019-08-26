package com.dengjk.system.security.config;

import com.alibaba.fastjson.JSON;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.common.utils.SpringSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Dengjk
 * @create 2019-08-22 22:58
 * @desc 退出登入操作
 */
@Component
@Slf4j
public class LogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("用户:{},退出登入成功", SpringSecurityUtils.getCurrentUserLogin());
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(ResultUtil.success("退出成功")));
    }
}
