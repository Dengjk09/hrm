package com.dengjk.common.enums;

/**
 * @author Dengjk
 * @create 2018-06-24 9:17
 * @desc  rest响应码
 **/
public enum HttpStatus {

    SUCCESS(200,"成功"),
    FAIL(400,"失败"),
    AUTH_FAIL(403, "校验错误"),
    NOT_EXIST(404, "不存在"),
    SEVER_ERROR(500, "服务错误"),
    VERIFIY_FAIL(402, "商品校验错误");

    private int code;
    private String msg;

    HttpStatus(int code, String msg){
        this.setCode(code);
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
