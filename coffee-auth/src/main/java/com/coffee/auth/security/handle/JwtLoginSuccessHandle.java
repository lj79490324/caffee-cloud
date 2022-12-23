package com.coffee.auth.security.handle;

import com.coffee.common.core.R;
import com.coffee.common.core.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录成功处理器
 *
 * @author rabit
 */
@Slf4j
@Component
public class JwtLoginSuccessHandle implements AuthenticationSuccessHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String token = JwtUtil.generateToken(authentication.getName());
        Cookie cookie = new Cookie(JwtUtil.TOKEN_HEADER, JwtUtil.TOKEN_PREFIX + token);
        cookie.setPath("/");
        cookie.setMaxAge(JwtUtil.TTL_COOKIE);
       // response.addCookie(cookie);
        Map<String,Object> map =new HashMap<>();
        map.put("token",token);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        // TODO: 2022/4/1 后期会加上ip校验保证多终端登录或者token盗用
        response.getWriter().write(objectMapper.writeValueAsString(R.ok(map)));
        response.flushBuffer();
    }
}
