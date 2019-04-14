package com.dengjk.common.utils;


import com.dengjk.common.enums.HttpStatus;

/**
 * @author Dengjk
 * @create 2018-04-28 14:23
 * @desc ResultUtil
 **/
public class ResultUtil {
    public static Result success(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.SUCCESS.getCode());
        result.setMsg(HttpStatus.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result authFail(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.AUTH_FAIL.getCode());
        result.setMsg(HttpStatus.AUTH_FAIL.getMsg() + ":" + object.toString());
        result.setData(null);
        return result;
    }

    public static Result fail(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.FAIL.getCode());
        result.setMsg(HttpStatus.FAIL.getMsg() + ":" + object.toString());
        result.setData(null);
        return result;
    }

    public static Result notExist(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.NOT_EXIST.getCode());
        result.setMsg(HttpStatus.NOT_EXIST.getMsg() + ":" + object.toString());
        result.setData(null);
        return result;
    }

    public static Result severError(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.SEVER_ERROR.getCode());
        result.setMsg(HttpStatus.SEVER_ERROR.getMsg() + ":" + object.toString());
        result.setData(null);
        return result;
    }

    public static Result verifiyError(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.VERIFIY_FAIL.getCode());
        result.setMsg(HttpStatus.VERIFIY_FAIL.getMsg());
        result.setData(object);
        return result;
    }

}
