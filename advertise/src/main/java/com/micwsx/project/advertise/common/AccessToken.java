package com.micwsx.project.advertise.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AccessToken {
    private String token;
    private int expireIn;
    private long expiryTime;

    public AccessToken() {
    }

    public AccessToken(String token, int expireIn) {
        this.token = token;
        this.expireIn = expireIn;
        this.expiryTime = System.currentTimeMillis() + expireIn * 1_000;
    }

    public String getToken() {
        return token;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", expireIn=" + expireIn +
                ", expiryTime=" + expiryTime +
                '}';
    }

    public static void main(String[] args) {

        AccessToken token=new AccessToken("dsffwefewfe",78);
        String jsonString= JSON.toJSONString(token);
        System.out.println(jsonString);
        AccessToken token1=JSONObject.parseObject(jsonString,AccessToken.class);
        System.out.println(token1);

    }
}
