package com.coffee.auth.security.jwt;

import com.coffee.auth.security.cache.RedisUserCache;
import com.coffee.auth.security.handle.JwtLoginFailHandle;
import com.coffee.auth.security.handle.JwtLoginSuccessHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * jwt相关的配置串联
 * @author rabit
 * @version v1.0
 * @date 2022/9/4 19:00
 */
@Component
public class JwtAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private JwtLoginSuccessHandle jwtLoginSuccessHandle;

    @Autowired
    private JwtLoginFailHandle jwtLoginFailHandle;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisUserCache redisUserCache;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtTokenAuthenticationFilter =new JwtAuthenticationFilter();
        jwtTokenAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        jwtTokenAuthenticationFilter.setAuthenticationSuccessHandler(jwtLoginSuccessHandle);
        jwtTokenAuthenticationFilter.setAuthenticationFailureHandler(jwtLoginFailHandle);
        JwtAuthenticationProvider jwtAuthenticationProvider=new JwtAuthenticationProvider();
        jwtAuthenticationProvider.setRedisUserCache(redisUserCache);
        jwtAuthenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(jwtAuthenticationProvider).addFilterAfter(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
