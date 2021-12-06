package com.evan.wj.result;

import io.netty.handler.codec.DecoderResult;

import java.io.Serializable;

public class Result<T>{
    //响应码
    private String code;
    private boolean success;
    private T data;
    private  String msg;

    public Result() {
        this.code = "200";
        this.success = true;
        this.msg = "successfully done empty result.";
    }
    public Result(String code, boolean success, T data, String msg) {
        this.code = code;
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public Result(T data) {
        this.code = "200";
        this.success = true;
        this.data = data;
        this.msg = "successfully done.";
    }

    public Result(String code, boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
