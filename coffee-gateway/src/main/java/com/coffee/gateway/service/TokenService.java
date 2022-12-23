package com.coffee.gateway.service;

import com.coffee.common.core.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通过服务调用token相关信息
 * @author rabit
 * @version v1.0
 * @date 2022/9/4 0:53
 */
@FeignClient(contextId = "tokenService",value = "coffee-auth")
public interface TokenService {

    @GetMapping("/check")
    R<?> checkPermission(@RequestHeader("token") String token, @RequestParam("reqPath")String reqPath,@RequestParam("method")String method);

}
