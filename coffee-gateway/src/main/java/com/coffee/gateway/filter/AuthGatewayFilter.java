package com.coffee.gateway.filter;

import com.coffee.common.core.Constant;
import com.coffee.common.core.R;
import com.coffee.common.core.utils.UrlUtils;
import com.coffee.gateway.config.properties.IgnoreWhiteProperties;
import com.coffee.gateway.order.OrderConstant;
import com.coffee.gateway.service.AuthService;
import com.coffee.gateway.utils.RequestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class AuthGatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private IgnoreWhiteProperties ignoreWhiteProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.error(exchange.getLogPrefix() + "---进入鉴权过滤器开始鉴权----");
        ServerHttpRequest request = exchange.getRequest();
        //获取本次访问网关的路径
        String path = request.getURI().getPath();
        log.info("1访问的路径是{},参数是：{}", path, request.getQueryParams());
        //跳过白名单
        if (UrlUtils.isMatches(ignoreWhiteProperties.getIgnoreList(), path)) {
            log.info("{}在白名单中直接放行", path);
            return chain.filter(exchange);
        }
        //验证用户权限和信息
        //1.获取token
        String token = RequestUtils.getToken(request);
        log.info("获取到的token={}", token);
        boolean flag = false;
        try {
            //验证访问权限
            flag = authService.checkPermission(token, UrlUtils.removeServerNamePrefix(path), request.getMethodValue());
        }catch (Exception e){
            return unauthorizedResponse(exchange,Constant.TOKEN_ERROR_CODE, "网关信息:"+e.getMessage());
        }
        //开发模式，flag直接放行
        if (true){
            return chain.filter(exchange);
        }else {
            return unauthorizedResponse(exchange,Constant.PERMISSION_DENIED_CODE, "网关信息:无访问权限，请联系管理员");
        }

    }


    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, int code,String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        String contentType = RequestUtils.getHeaderByKey(exchange.getRequest(), "Content-Type");
        if (contentType != null) {
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        }
        R res = R.error(code, msg);
        DataBuffer dataBuffer = null;
        try {
            dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(res));
        } catch (JsonProcessingException e) {
            log.error("json处理出问题：{}", e.getMessage());
            e.printStackTrace();
        }
        assert dataBuffer != null;
        return response.writeWith(Mono.just(dataBuffer));
    }



    @Override
    public int getOrder() {
        return OrderConstant.AUTH_INDEX;
    }
}
