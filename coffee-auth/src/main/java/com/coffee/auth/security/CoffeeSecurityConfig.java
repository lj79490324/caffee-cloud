package com.coffee.auth.security;

import com.coffee.auth.contanst.SecurityUrlParam;
import com.coffee.auth.security.cache.RedisUserCache;
import com.coffee.auth.security.fliter.CoffeeGlobalAuthenticationFailureHandler;
import com.coffee.auth.security.fliter.JwtGlobalAuthorizationFilter;
import com.coffee.auth.security.jwt.JwtAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CoffeeSecurityConfig {

    @Autowired
    private RedisUserCache redisUserCache;
    @Autowired
    private JwtAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private CoffeeGlobalAuthenticationFailureHandler coffeeGlobalAuthenticationFailureHandler;
//    @Autowired
//    private CoffeeAccessDecisionManager coffeeAccessDecisionManager;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                //有部分地址需要匿名访问，配置不做验证的接口地址，此处配置后接口地址就直接不经过Security过滤器链
//                .antMatchers("/api/**","/send/**");
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtGlobalAuthorizationFilter jwtGlobalAuthorizationFilter = new JwtGlobalAuthorizationFilter();
        jwtGlobalAuthorizationFilter.setRedisUserCache(redisUserCache);
        jwtGlobalAuthorizationFilter.setAuthenticationFailureHandler(coffeeGlobalAuthenticationFailureHandler);
        http.addFilterBefore(jwtGlobalAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().apply(jwtAuthenticationConfig).and().authorizeRequests().antMatchers(SecurityUrlParam.JWT_LOGIN_URL,"/v3/api-docs").permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
        return http.build();
    }
}
