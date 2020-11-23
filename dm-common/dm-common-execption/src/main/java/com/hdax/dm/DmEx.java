package com.hdax.dm;

/**
 * 自定义异常父类
 */
public class DmEx  extends  RuntimeException{
    protected  String errCode;

     public DmEx(){

     }
    public DmEx(String errCode,String message ){
        super(message);
        this.errCode =errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
