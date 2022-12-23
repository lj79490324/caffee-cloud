package com.coffee.auth.feign.factory;

import com.coffee.auth.feign.RouterSysMenuService;
import com.coffee.common.core.R;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class RouterSysMenuFallbackFactory implements FallbackFactory<RouterSysMenuService> {
    @Override
    public RouterSysMenuService create(Throwable cause) {
        return userId -> R.error("获取菜单失败");
    }
}
