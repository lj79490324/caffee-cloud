package com.coffee.auth.security.jwt;

import com.coffee.auth.security.cache.RedisUserCache;
import com.coffee.auth.security.model.SecurityUserDetails;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



public class JwtAuthenticationProvider implements AuthenticationProvider {

    private RedisUserCache redisUserCache;

    private UserDetailsService userDetailsService;
    //密码验证器
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //账号状态检测
    private final DbUserDetailsChecker checker = new DbUserDetailsChecker();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        SecurityUserDetails securityUserDetails = jwtAuthenticationToken.getSecurityUserDetails();
        //设置用户信息是否已经被缓存
        boolean cacheWasUsed = true;
        //从缓存中获取用户信息
        SecurityUserDetails dbUserDetails = (SecurityUserDetails) redisUserCache.getUserFromCache(securityUserDetails.getUsername());
        //如果没有从缓存中读取出来，从数据库中查询
        if (dbUserDetails == null){
            try {
                dbUserDetails =getUserDetails(securityUserDetails.getUsername());
            }catch (AuthenticationException e){
                cacheWasUsed = false;
                throw new BadCredentialsException(e.getMessage());
            }
        }
        try {
            //检查用户账号是否可以登录
            checker.check(dbUserDetails);
            //检测密码是否正确
            additionalAuthenticationChecks(dbUserDetails, jwtAuthenticationToken);
        }catch (AuthenticationException ex){
            if (!cacheWasUsed){
                throw ex;
            }
            cacheWasUsed = false;
            //当用户是从缓存中读取信息的时候，需要从数据库中获取最新的信息进行校验
            dbUserDetails =getUserDetails(securityUserDetails.getUsername());
            //检查用户账号是否可以登录
            checker.check(dbUserDetails);
            //检测密码是否正确
            additionalAuthenticationChecks(dbUserDetails, jwtAuthenticationToken);
        }
        if (cacheWasUsed){
            redisUserCache.putUserInCache(dbUserDetails);
        }
        JwtAuthenticationToken resultToken =new JwtAuthenticationToken(dbUserDetails);
        resultToken.setDetails(dbUserDetails);
        return resultToken;
    }

    private void additionalAuthenticationChecks(SecurityUserDetails dbUserDetails, JwtAuthenticationToken jwtAuthenticationToken) {
        if (jwtAuthenticationToken.getCredentials() == null) {
            throw new BadCredentialsException("用户输入的密码不能为空");
        }
        String presentedPassword = jwtAuthenticationToken.getCredentials().toString();
        if (!this.passwordEncoder.matches(presentedPassword, dbUserDetails.getPassword())) {
            throw new BadCredentialsException("密码不匹配，验证失败");
        }
    }

    /**
     * 通过用户名获取用户信息
     * @param userName 用户名
     * @return 返回用户详情
     */
    private SecurityUserDetails getUserDetails(String userName){
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(userName);
        }catch (Exception e){
            throw new BadCredentialsException(e.getMessage());
        }

        if (userDetails == null){
            throw new InternalAuthenticationServiceException("用户认证失败");
        }
        return (SecurityUserDetails) userDetails;
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public RedisUserCache getRedisUserCache() {
        return redisUserCache;
    }

    public void setRedisUserCache(RedisUserCache redisUserCache) {
        this.redisUserCache = redisUserCache;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    static class DbUserDetailsChecker implements UserDetailsChecker {

        @Override
        public void check(UserDetails toCheck) {
            SecurityUserDetails user = null;
            if (toCheck instanceof SecurityUserDetails){
                user = (SecurityUserDetails) toCheck;
            }
            if (user != null){
                // 4已锁定，3过期，2异常，1禁止登录，0正常
                if (user.getStatus() == 1){
                    throw new DisabledException("账号禁止登录");
                }
                if (user.getStatus() == 2){
                    throw new AccountExpiredException("账号状态异常");
                }
                if (user.getStatus() == 3){
                    throw new AccountExpiredException("账号已经过期");
                }
                if (user.getStatus() == 4){
                    throw new LockedException("账号已经被锁定");
                }
            }
        }
    }
}
