package com.emall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 定义服务器的返回对象
 *
 * @author Harry Chou
 * @date 2019/5/23
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    // 请求是否成功
    @JsonIgnore
    public boolean isSuccessful() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    // 得到响应成功的状态码
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
    }

    // 得到响应成功的状态码与信息
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg);
    }

    // 得到响应成功的状态码与数据
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), data);
    }

    // 得到响应成功的状态码、信息与数据
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    // 得到响应失败的状态码与描述
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    // 得到响应失败的状态码与信息
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    // 得到响应失败的错误信息
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ServerResponse<>(errorCode, errorMessage);
    }

}
