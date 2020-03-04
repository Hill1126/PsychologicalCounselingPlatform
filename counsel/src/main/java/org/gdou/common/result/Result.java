package org.gdou.common.result;


/**
 * 统一API响应结果封装
 * @Author: HILL
 * @date: 2020/3/2 20:29
 *
**/
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

}
