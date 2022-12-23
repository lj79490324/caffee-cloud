package com.coffee.common.core;

/**
 * 系统返回信息常量
 * @author rabit
 * @version v1.0
 * @date 2022/8/31 19:17
 */
public class Constant {

    //请求成功的返回状态码
    public static final Integer OK_CODE = 0;

    //请求成功的提示信息
    public static final String OK_MSG = "请求成功";

    //请求失败的返回的状态码
    public static final Integer ERROR_CODE = 1;

    //请求失败的提示信息
    public static final String ERROR_MSG = "请求失败";

    //权限正常访返回状态码
    public static final Integer PERMISSION_ACCESS_CODE = 200;

    //无访问权限返回状态码
    public static final Integer PERMISSION_DENIED_CODE = 503;

    //token异常返回状态码
    public static final Integer TOKEN_ERROR_CODE = 500;
}
