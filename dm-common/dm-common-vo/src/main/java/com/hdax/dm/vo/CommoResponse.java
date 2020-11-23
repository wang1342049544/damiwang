package com.hdax.dm.vo;

import java.util.List;

/**
 * 结合前端返回结构来定义 咱们公共相应类型
 * 所以将map集合 封装出来  就不需要再每一次控制器返回的时候 都掉用
 */
public class CommoResponse<T>{
    private  String success;
    private  String errorCode;
    private   String msg;
    private   T data;


    //构造方法  成功数据的赋值操作
    public  CommoResponse(String msg,T data){
        System.out.println("响应成功");
        this.success = "0000";
        this.errorCode = "0000";
        this.msg = msg;
        this.data = data;
    }


    //构造方法  失败数据的赋值操作
    //失败了肯定没数据了
    public  CommoResponse(String msg,String errCode){
        System.out.println("响应失败");
        this.errorCode = errCode;
        this.msg = msg ;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
