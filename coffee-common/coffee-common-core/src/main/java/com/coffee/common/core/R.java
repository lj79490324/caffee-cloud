package com.coffee.common.core;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 请求返回值
 * @author rabit
 * @version v1.0
 * @date 2022/8/31 19:18
 */
@Schema(description = "返回结果")
public class R<T> {

    @Schema(description = "接口运行结果状态码")
    private int code;
    @Schema(description = "程序运行结果提示")
    private String msg;
    @Schema(description = "程序最终返回数据，如果没有则为空")
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
