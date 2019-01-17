package com.chengma.devplatform.common.dao;

import com.chengma.devplatform.common.util.JSONUtils;

/**
 * Created by Administrator on 2017/7/12/0012.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

//import com.alibaba.druid.support.json.JSONUtils;

public class ResponseResult<T> {
    public static final String SUCCESS_CODE = "0000";
    public static final String EXCEPTION_CODE = "0500";
    public static final String FAIL_CODE = "0100";
    public static final String AUTH_FAIL_CODE = "0403";
    public static final String LOGIN_FAIL_CODE = "0402";
    public static final String LOGIN_REQUIRE_CODE = "0401";
    private String statusCode;
    private String msgCode;
    private T data;
    private T userInfo;

    public ResponseResult() {
        this.statusCode = "0100";
    }

    public ResponseResult(T data) {
        this.statusCode = "0100";
        this.setData(data);
    }

    public ResponseResult(String statusCode, String msgCode) {
        this(statusCode, msgCode, null);
    }

    public ResponseResult(String statusCode, String msgCode, T data) {
        this(data);
        this.setStatusCode(statusCode);
        this.setMsgCode(msgCode);
    }

    public String getMsgCode() {
        return this.msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> createSuccessResult(String msg, T data) {
        return new ResponseResult("0000", msg, data);
    }

    public static <T> ResponseResult<T> createFailResult(String msg, T data) {
        return new ResponseResult("0100", msg, data);
    }

    public static <T> ResponseResult<T> createExceptionResult(String msg, T data) {
        return new ResponseResult("0500", msg, data);
    }

    public static <T> ResponseResult<T> createAuthFailResult(T data) {
        return new ResponseResult("0403", "无权限", data);
    }

    public static <T> ResponseResult<T> createLoginFailResult(T data) {
        return new ResponseResult("0403", "登录失败", data);
    }

    public static <T> ResponseResult<T> createLoginRequireResult(T data) {
        return new ResponseResult("0403", "未登录", data);
    }

    public T getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(T userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        try {
            return JSONUtils.obj2json(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
