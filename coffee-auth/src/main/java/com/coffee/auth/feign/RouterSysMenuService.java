package com.coffee.auth.feign;

import com.coffee.auth.feign.factory.RouterSysMenuFallbackFactory;
import com.coffee.common.core.R;
import com.coffee.common.core.ServiceNameConstant;
import com.coffee.system.dto.SysMenuDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
/**
 * @author rabit
 */
@FeignClient(contextId = "routerSysMenuService",value = ServiceNameConstant.SYSTEM_SERVICE_NAME,path = "sysMenu",fallbackFactory = RouterSysMenuFallbackFactory.class)
public interface RouterSysMenuService {

    @GetMapping("/all/{userId}")
    R<List<SysMenuDto>> getMenuByUserId(@PathVariable("userId") Long userId);
}
