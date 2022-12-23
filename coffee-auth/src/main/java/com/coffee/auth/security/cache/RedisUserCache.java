package com.coffee.auth.security.cache;


import com.coffee.auth.security.model.SecurityUserDetails;
import com.coffee.common.core.utils.JwtUtil;
import com.coffee.common.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class RedisUserCache implements UserCache {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public UserDetails getUserFromCache(String username) {
        Object o = redisService.get(JwtUtil.PREFIX_KEY + username);
        SecurityUserDetails securityUserDetails = null;
        try {
            securityUserDetails = objectMapper.readValue(objectMapper.writeValueAsString(o), SecurityUserDetails.class);
            redisService.expire(JwtUtil.PREFIX_KEY + username,JwtUtil.TTL/1000);
        } catch (JsonProcessingException e) {
            removeUserFromCache(username);
            e.printStackTrace();
        }
        return securityUserDetails;
    }

    @Override
    public void putUserInCache(UserDetails user) {
        redisService.set(JwtUtil.PREFIX_KEY+user.getUsername(),user, JwtUtil.TTL/1000);
    }

    @Override
    public void removeUserFromCache(String username) {
        redisService.del(JwtUtil.PREFIX_KEY+username);
    }
}
