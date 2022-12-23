package com.coffee.gateway.utils;

import com.coffee.common.core.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

/**
 * 请求信息获取工具，可以用来获取ip，请求时间，请求参数，请求头等
 * @author rabit
 * @version v1.0
 * @date 2022/9/4 2:27
 */
public class RequestUtils {

    /**
     * 从ServerHttpRequest中获取token
     * token获取优先级： 请求头 > cookie > 参数
     * 当三个环境中都有token的时候，只能获取到请求头中的
     * @param request 请求信息
     * @return 获取的token，可能为null
     */
    public static String getToken(ServerHttpRequest request){
        String token = null;
        //设置一个获取标识，处理token的获取优先级
        boolean isNotGetToken = true;
        HttpHeaders headers = request.getHeaders();

        //从http header中获取token
        String tmpHeadToken = headers.getFirst(JwtUtil.TOKEN_HEADER);
        if (StringUtils.hasLength(tmpHeadToken)){
            token = tmpHeadToken;
            isNotGetToken = false;
        }
        //从cookie中获取token
        String cookieString = isNotGetToken?headers.getFirst("Cookie"):null;
        if (cookieString != null) {
            for (String item : cookieString.split(";")) {
                String[] split = item.split("=");
                System.out.println(split[1]);
                if (split.length==2 && JwtUtil.TOKEN_HEADER.equals(split[0].trim())){
                    token = split[1].isEmpty()?null:split[1].trim();
                    isNotGetToken = false;
                    break;
                }
            }
        }
        // 从参数中获取token
        String paramToken = isNotGetToken?request.getQueryParams().getFirst(JwtUtil.TOKEN_HEADER):null;
        if (isNotGetToken && StringUtils.hasLength(paramToken)) {
            token = paramToken;
        }
        // 校验token头
        if (StringUtils.hasLength(token) && token.startsWith(JwtUtil.TOKEN_PREFIX)) {
            token= token.substring(JwtUtil.TOKEN_PREFIX.length());
        }
        // 解析token
        return token;
    }

    /**
     * 通过key从请求信息中获取数值
     * @param request 请求信息
     * @param key key
     * @return 返回结果
     */
    public static String getHeaderByKey(ServerHttpRequest request,String key){
        return request.getHeaders().getFirst(key);
    }


}
