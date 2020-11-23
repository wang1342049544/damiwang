package com.hdax.dm.vo;


import com.hdax.dm.DmEx;

import java.util.List;

/**
 * CommoResponse类 弄好之后
 * 声明一个工具类 类直接 点操作 这么方法
 * 不需要每次都new它  方法名称就定义为成功或者失败
 * @return
 */

public class VoUtil<T> {
    /**
     * 成功的相应
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
     public static<T>  CommoResponse<T> returnSuccess(String msg, T data){

         return  new CommoResponse<T>(msg,data);
     }

    public static<T>  CommoResponse<T> re(String msg, T data){

        return  new CommoResponse<T>(msg,data);
    }


    /**
     * 失败的响应
     */
    public static<T>  CommoResponse<T> returnSuccess(String msg,String errorCode){
        return  new CommoResponse<T>(msg,errorCode);
    }
    /**
     * 失败的异常
     */
    public static<T>  CommoResponse<T> returnSuccess(DmEx exception, String msg){
        return  new CommoResponse<T>(exception.getErrCode(),msg);
    }

    public static<T> CommoResponse<T> returnFail(String errorCode,String msg){
        return new CommoResponse<T>(errorCode,msg);
    }

    /**
     * 手机号格式不正确
     *
     */
    public static<T> CommoResponse<T> returnFailure(String errorCode, String msg){
        return new CommoResponse<T>(errorCode, msg);
    }



}
