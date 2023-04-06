package com.coffee.auth.security.fliter;

import com.coffee.auth.contanst.SecurityUrlParam;
import com.coffee.auth.security.cache.RedisUserCache;
import com.coffee.auth.security.exception.TokenAuthenticationException;
import com.coffee.auth.security.jwt.JwtAuthenticationToken;
import com.coffee.auth.security.model.SecurityUserDetails;
import com.coffee.common.core.utils.JwtUtil;
import com.coffee.common.redis.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 全局security过滤器，此处用来过滤token
 * @author rabit
 * @version v1.0
 * @date 2022/9/4 19:40
 */

public class JwtGlobalAuthorizationFilter extends OncePerRequestFilter {


    private RedisUserCache redisUserCache;

    private ObjectMapper objectMapper = new ObjectMapper();

    private AuthenticationFailureHandler authenticationFailureHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //白名单不校验,除开白名单的所有的都要进行token校验
        if (!request.getRequestURI().equals(SecurityUrlParam.JWT_LOGIN_URL) && !request.getRequestURI().equals("/v3/api-docs")){
            String headToken = "";
            //从http header中获取token
            if (StringUtils.isNotEmpty(request.getHeader(JwtUtil.TOKEN_HEADER))){
                headToken = request.getHeader(JwtUtil.TOKEN_HEADER);
            }

            Cookie[] cookies =request.getCookies();
            if (StringUtils.isEmpty(headToken) && cookies!=null){
                for (Cookie cookie:cookies){
                    if (JwtUtil.TOKEN_HEADER.equals(cookie.getName()) && StringUtils.isNotEmpty(cookie.getValue())){
                        headToken = cookie.getValue();
                        break;
                    }
                }
            }
            // 从参数中获取token
            if (StringUtils.isEmpty(headToken) && StringUtils.isNotEmpty(request.getParameter(JwtUtil.TOKEN_HEADER))) {
                headToken = request.getParameter(JwtUtil.TOKEN_HEADER);
            }
            try {
            // 校验token头
            if (StringUtils.isEmpty(headToken) || !headToken.startsWith(JwtUtil.TOKEN_PREFIX)) {
                throw new TokenAuthenticationException("token异常");
            }
            // 解析token
            String token = headToken.substring(JwtUtil.TOKEN_PREFIX.length());
            Claims claims = JwtUtil.parseToken(token);
            // 校验token是否过期
            if (JwtUtil.isExpiration(claims)) {
                throw new TokenAuthenticationException("token已过期");
            }

            String username = JwtUtil.getUsername(claims);
            if (StringUtils.isEmpty(username)) {
                throw new UsernameNotFoundException("用户名异常");
            }
            //从缓存中获取用户信息
            Object userObj = redisUserCache.getUserFromCache(username);
            if (userObj == null){
                throw new DisabledException("用户已经退出登录");
            }
                SecurityUserDetails securityUserDetails = objectMapper.readValue(objectMapper.writeValueAsString(userObj), SecurityUserDetails.class);
                securityUserDetails.setEnabled(true);
                JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(securityUserDetails);
                jwtToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtToken);
                filterChain.doFilter(request,response);
            }catch (AuthenticationException ex){
//                ex.printStackTrace();
                authenticationFailureHandler.onAuthenticationFailure(request,response,ex);
            }catch (JwtException ex){
//                ex.printStackTrace();
                authenticationFailureHandler.onAuthenticationFailure(request,response,new AuthenticationServiceException("token异常"));
            }
        }else {
            filterChain.doFilter(request,response);
        }

    }


    public void setRedisUserCache(RedisUserCache redisUserCache) {
        this.redisUserCache = redisUserCache;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
