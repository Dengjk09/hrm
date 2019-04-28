package com.dengjk.common.exception;

import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author Dengjk
 * @create 2018-05-21 13:56
 * @desc ControllerAdvice用于请求进入controller层的处理
 * 有三个注解 @@InitBinder 初始化的数据绑定
 * @ModelAttribute 绑定模型数据  在controller可以通过该注解获取@ModelAttribute
 **/
@RestControllerAdvice
@Slf4j
public class ControllerException {


    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result validExceptionHandler(Exception e) {
        String errMsg = null;
        if (e instanceof BindException) {
            errMsg = ((BindException) e).getBindingResult().getFieldErrors()
                    .stream().map(error -> error.getField() + ":" + error.getDefaultMessage())
                    .collect(Collectors.joining(","));
        } else if (e instanceof MethodArgumentNotValidException) {
            errMsg = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors()
                    .stream().map(error -> error.getField() + ":" + error.getDefaultMessage())
                    .collect(Collectors.joining(","));
        }
        log.warn(errMsg.toString());
        return ResultUtil.authFail(errMsg);
    }

    @ExceptionHandler(value = {Exception.class, MyException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result defaultErrorHandler(Exception e) {
        if (e instanceof IllegalArgumentException) {
            log.error("请求失败:", e);
            return ResultUtil.fail(e.getMessage());
        } else {
            log.error("请求失败:", e);
            return ResultUtil.severError(e.getMessage());
        }
    }

    /***
     * 权限相关处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {LoginErrorException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result loginErrorHandler(Exception e) {
        return ResultUtil.authFail(e.getMessage());
    }


    /**
     * 处理shiro注解配置权限,而权限不足抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {AuthorizationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result authorizationHandler(Exception e) {
        return ResultUtil.authFail("抱歉,没有权限,请联系管理员");
    }
}
