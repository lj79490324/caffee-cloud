package com.coffee.gateway.service;

import com.coffee.common.core.utils.JwtUtil;
import com.coffee.common.core.utils.UrlUtils;
import com.coffee.common.redis.service.RedisService;
import com.coffee.system.model.SysMenu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 权限验证
 * @author rabit
 * @version v1.0
 * @date 2022/9/9 12:13
 */
@Slf4j
@Service
public class AuthService {


    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    public boolean checkPermission(String token, String removeServerNamePrefix, String methodValue) {
        // 解析token
        boolean hasPer = false;
        if (token.startsWith(JwtUtil.TOKEN_PREFIX)){
            token = token.substring(JwtUtil.TOKEN_PREFIX.length());
        }
        Claims claims = JwtUtil.parseToken(token);
        // 校验token是否过期
        if (JwtUtil.isExpiration(claims)) {
            throw new SecurityException("token过期");
        }

        String username = JwtUtil.getUsername(claims);
        if (StringUtils.isEmpty(username)) {
            throw new SecurityException("用户名异常");
        }

        Object o = redisService.get(JwtUtil.PREFIX_KEY + username);
        if (o != null){
            LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) o;
            Object sysMenuListMap = map.get("sysMenuList");
            try {
               String json =  objectMapper.writeValueAsString(sysMenuListMap);
                List<SysMenu> sysMenuList = objectMapper.readValue(json, new TypeReference<List<SysMenu>>() {
                });
                //设置只要登录后可以访问的白名单
                for (SysMenu sysMenu:sysMenuList){
                    if (UrlUtils.isMatchesNotMicoServer(removeServerNamePrefix, sysMenu.getSysMenuUrl())){
                        hasPer = true;
                        break;
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            throw new SecurityException("用户已经退出登录");
        }

        return hasPer;
    }
}
