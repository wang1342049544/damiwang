package com.hdax.dm.vo;

import java.io.Serializable;

public class DmUserResponse implements Serializable {
    private String token;//token:PC/MOBILE-md5()-userId-md5().substring(0,6)
    private Long extTime;
    private Long genTime;
    private Long userId;
    private String phone;
    private String realName;
    private String nickName;
    private Long sex;
    private String idCard;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExtTime() {
        return extTime;
    }

    public void setExtTime(Long extTime) {
        this.extTime = extTime;
    }

    public Long getGenTime() {
        return genTime;
    }

    public void setGenTime(Long genTime) {
        this.genTime = genTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
