package com.coffee.auth.security.jwt;

import com.coffee.auth.contanst.SecurityUrlParam;
import com.coffee.auth.security.model.SecurityUserDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

/**
 * 用来验证用户名和密码登录的请求过滤器
 * @author rabit
 * @version v1.0
 * @date 2022/9/4 18:13
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final static String USER_NAME = "username";
    private final static String PASSWORD = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(SecurityUrlParam.JWT_LOGIN_URL, SecurityUrlParam.JWT_LOGIN_METHOD);
    private boolean postOnly = true;

    public JwtAuthenticationFilter(){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }
    /**
     * 真正认证流程，添加未认证用户认证信息，然后在provider里面进行正式认证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equalsIgnoreCase("POST")){
            throw new AuthenticationServiceException("认证方法不允许是POST之外的方法");
        }
        //从请求体中获取账号和密码
        Map<String,String> map = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<Map<String,String>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        String userName = map.get(USER_NAME);
        String password = map.get(PASSWORD);

        //从查询参数中获取账号和密码
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
             userName = obtainUserName(request);
             password = obtainPassword(request);
        }

        userName = (userName!=null)?userName.trim():"";
        password = (password != null)?password.trim():"";
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            throw new UsernameNotFoundException("用户名或者密码必填");
        }
        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setUsername(userName);
        securityUserDetails.setPassword(password);
        JwtAuthenticationToken jwtAuthenticationToken =new JwtAuthenticationToken(securityUserDetails);
        setDetails(request,jwtAuthenticationToken);
        return this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
    }

    /**
     * 获取密码
     * @param request
     * @return
     */
    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(PASSWORD);
    }

    /**
     * 获取手机号
     * @param request
     * @return
     */
    private String obtainUserName(HttpServletRequest request) {
        return request.getParameter(USER_NAME);
    }

    protected void setDetails(HttpServletRequest request, JwtAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
