package com.dengjk.common.exception;

import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
public class ControllerException {

    private Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);


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
        logger.warn(errMsg.toString());
        return ResultUtil.authFail(errMsg);
    }

    @ExceptionHandler(value = {Exception.class, MyException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result defaultErrorHandler(Exception e) {
        if (e instanceof IllegalArgumentException) {
            logger.error("请求失败:", e);
            return ResultUtil.fail(e.getMessage());
        } else {
            logger.error("请求失败:", e);
            return ResultUtil.severError(e.getMessage());
        }
    }
}
