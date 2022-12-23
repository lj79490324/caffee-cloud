package com.coffee.auth.security.fliter;

import com.coffee.common.core.Constant;
import com.coffee.common.core.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CoffeeGlobalAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (log.isDebugEnabled()){
            exception.printStackTrace();
        }
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        log.error("登录失败：{}",exception.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(R.error(Constant.TOKEN_ERROR_CODE,exception.getMessage())));
        response.flushBuffer();
    }
}
