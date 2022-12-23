package com.coffee.gateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 通行白名单配置
 * @author rabit
 * @version v1.0
 * @date 2022/9/3 19:15
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "gateway.ignore")
public class IgnoreWhiteProperties {

    public IgnoreWhiteProperties() {
        this.ignoreList.add("/jwt/login");
        this.ignoreList.add("/jwt/logout");
        this.ignoreList.add("/user/info");
        this.ignoreList.add("/user/menu");
    }

    //存储网关不检查的白名单
    private List<String> ignoreList = new ArrayList<>();

    public List<String> getIgnoreList() {
        return ignoreList;
    }

    public void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }
}
