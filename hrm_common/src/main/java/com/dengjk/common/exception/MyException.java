package com.dengjk.common.exception;

/**
 * @author Dengjk
 * @create 2018-07-06 10:53
 * @desc
 **/
public class MyException extends RuntimeException {
    public MyException(){};
    public  MyException(String msg){
        super(msg);
    }
}
