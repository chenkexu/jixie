package com.qmwl.zyjx.api;

import java.io.Serializable;

/**
 * Created by huang on 2018/4/16.
 */

public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = -8219180758532465806L;
    private int code;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public T getResult() {
//        return result;
//    }
//
//    public void setResult(T result) {
//        this.result = result;
//    }
}
