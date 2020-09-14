package com.micwsx.project.advertise.viewmodel;

public class BusinessException extends RuntimeException {
    private Integer code;
    private String url;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "url=" + url + "\r\ncode=" + code + "\r\nmessage=" + getMessage();
    }
}
