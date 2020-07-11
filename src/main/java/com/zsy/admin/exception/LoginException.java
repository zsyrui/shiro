package com.zsy.admin.exception;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-07 14:13
 */
public class LoginException extends Exception{
    public LoginException(){
        super();
    }
    //用详细信息指定一个异常
    public LoginException(String message){
        super(message);
    }

    //用指定的详细信息和原因构造一个新的异常
    public LoginException(String message, Throwable cause){
        super(message,cause);
    }

    //用指定原因构造一个新的异常
    public LoginException(Throwable cause) {
        super(cause);
    }
}
