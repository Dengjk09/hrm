package com.dengjk.common.exception;

/**
 * @author Dengjk
 * @create 2019-04-20 16:46
 * @desc
 **/
public class LoginErrorException  extends Exception{
    public LoginErrorException(){};
    public  LoginErrorException(String msg){
        super(msg);
    }
}
