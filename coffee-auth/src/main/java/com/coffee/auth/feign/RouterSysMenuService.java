package com.coffee.auth.feign;

import com.coffee.auth.feign.factory.RouterSysMenuFallbackFactory;
import com.coffee.common.core.R;
import com.coffee.common.core.ServiceNameConstant;
import com.coffee.system.model.SysMenu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(contextId = "routerSysMenuService",value = ServiceNameConstant.SYSTEM_SERVICE_NAME,path = "sysMenu",fallbackFactory = RouterSysMenuFallbackFactory.class)
public interface RouterSysMenuService {

    @GetMapping("/all/{userId}")
    R<List<SysMenu>> getMenuByUserId(@PathVariable("userId") Long userId);
}
