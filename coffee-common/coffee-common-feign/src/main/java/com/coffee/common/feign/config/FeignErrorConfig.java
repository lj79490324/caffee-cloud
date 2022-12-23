package com.coffee.common.feign.config;

import feign.Response;
import feign.RetryableException;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class FeignErrorConfig implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = new Default().decode(methodKey, response);
        try {
            log.error("{}调用错误，错误状态码是{},异常信息是：{}", methodKey, response.status(), Util.toString(response.body().asReader(StandardCharsets.UTF_8)));
            if (exception instanceof RetryableException) {
                return exception;
            } else {
                // 可以自定义一些逻辑，比如抛出一个其他的(统一个异常)
                exception = new RuntimeException("服务调用错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exception;
    }
}
