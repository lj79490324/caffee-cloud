package com.coffee.gateway.order;

/**
 * 定义网关过滤器顺序，数值越小优先级越高
 * @author rabit
 * @version v1.0
 * @date 2022/9/2 1:06
 */
public class OrderConstant {
    //日志过滤器序号
    public static final int LOG_INDEX = -200;
    //鉴权过滤器的序号
    public static final int AUTH_INDEX = -100;
}
