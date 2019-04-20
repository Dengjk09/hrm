package com.dengjk.system.controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dengjk
 * @create 2019-04-20 22:58
 * @desc 基础controller ，主要减少 重复性的代码
 **/
public class BaseController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected String userId;

    protected String userName;

    protected Claims claims;


    /***
     * 设置一些参数
     *  @ModelAttribute 是指在真正进入controller之前的方法进行调用的
     */
    @ModelAttribute
    public void setRequestInfo(HttpServletRequest req, HttpServletResponse resp) {
        this.request = req;
        this.response = resp;
        /**过滤器中已经设置了这个值*/
        Object user_claims = req.getAttribute("user_claims");
        if (user_claims != null) {
            this.claims = (Claims) user_claims;
            this.userId = claims.getId();
            this.userName = claims.getSubject();
        }
    }
}
