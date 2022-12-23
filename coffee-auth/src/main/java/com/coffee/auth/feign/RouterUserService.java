package com.coffee.auth.feign;

import com.coffee.auth.feign.factory.RouterUserFallbackFactory;
import com.coffee.common.core.R;
import com.coffee.common.core.ServiceNameConstant;
import com.coffee.system.model.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "routerUserService",value = ServiceNameConstant.SYSTEM_SERVICE_NAME,path = "sysUser",fallbackFactory = RouterUserFallbackFactory.class)
public interface RouterUserService {

    @GetMapping("/info/{userName}")
    R<SysUser> getUserInfo(@PathVariable("userName") String userName);
}
