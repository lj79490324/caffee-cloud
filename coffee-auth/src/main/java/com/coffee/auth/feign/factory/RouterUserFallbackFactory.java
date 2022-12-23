package com.coffee.auth.feign.factory;

import com.coffee.auth.feign.RouterUserService;
import com.coffee.common.core.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RouterUserFallbackFactory implements FallbackFactory<RouterUserService> {
    @Override
    public RouterUserService create(Throwable cause) {
        log.error("报错了:{}",cause.getMessage());
        return userName -> R.error("获取用户失败");
    }
}
