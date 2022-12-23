package com.coffee.common.core;

import java.util.HashMap;

/**
 * 请求返回值
 * @author rabit
 * @version v1.0
 * @date 2022/8/31 19:18
 */
public class R<T> {

    private int code;
    private String msg;
    private T data;

    public R() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public static <T> R<T> ok(){
        return result(null,Constant.OK_CODE,Constant.OK_MSG);
    }

    public static <T> R<T> ok(String msg){
        return result(null,Constant.OK_CODE,msg);
    }

    public static <T> R<T> ok(T value){
        return result(value,Constant.OK_CODE,Constant.OK_MSG);
    }

    public static <T> R<T> ok(int code,T value){
        return result(value,code,Constant.OK_MSG);
    }

    public static <T> R<T> error(){
        return result(null,Constant.ERROR_CODE,Constant.ERROR_MSG);
    }

    public static <T> R<T> error(String msg){
        return result(null,Constant.ERROR_CODE,msg);
    }

    public static <T> R<T> error(Integer code,String msg){
        return result(null,code,msg);
    }

    public static <T> R<T> result(T data, int code, String msg){
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
