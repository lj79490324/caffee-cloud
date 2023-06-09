package com.coffee.auth.controller;

import com.coffee.auth.contanst.SecurityUrlParam;
import com.coffee.auth.dto.SysUserDto;
import com.coffee.auth.feign.RouterUserService;
import com.coffee.auth.security.cache.RedisUserCache;
import com.coffee.auth.security.exception.TokenAuthenticationException;
import com.coffee.auth.security.jwt.JwtAuthenticationToken;
import com.coffee.auth.security.model.SecurityUserDetails;
import com.coffee.common.core.R;
import com.coffee.system.dto.SysMenuDto;
import com.coffee.system.model.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户token处理，退出登录功能
 * @author rabit
 * @version v1.0
 * @date 2022/9/8 16:19
 */

@Tag(name = "认证模块",description = "获取用户相关信息")
@Slf4j
@RestController
public class AuthController {

    @Autowired
    private RedisUserCache redisUserCache;
    @Autowired
    private RouterUserService routerUserService;


    /**
     * 用户退出登录
     * @return 退出登录结果
     */
    @Operation(summary = "退出登录",description = "用户退出登录")
    @GetMapping(SecurityUrlParam.JWT_LOGOUT_URL)
    public R<?> logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
           throw new TokenAuthenticationException("token异常");
        }
        String userName = authentication.getPrincipal().toString();
        redisUserCache.removeUserFromCache(userName);
        return R.ok("退出登录成功");
    }

    /**
     * 已经登录的用户获取用户信息
     * @return 用户信息
     */
    @Operation(summary = "获取用户信息",description = "已经登录的用户获取用户信息")
    @GetMapping("/user/info")
    public R<SysUserDto> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new TokenAuthenticationException("token异常");
        }
        JwtAuthenticationToken jat = (JwtAuthenticationToken) authentication;
        String userName = jat.getPrincipal().toString();
        R<SysUser> userInfo = routerUserService.getUserInfo(userName);
        if (userInfo.getData() != null){
            SysUser sysUser = userInfo.getData();

            SysUserDto sysUserDto = SysUserDto.builder()
                    .userName(sysUser.getUsername())
                    .photo(null)
                    .time(sysUser.getLastLoginTime())
                    .roles(new ArrayList<>(jat.getSecurityUserDetails().getRoles()))
                    .authBtnList(new ArrayList<>(jat.getSecurityUserDetails().getAuthBtnList())).build();
            return R.ok(sysUserDto);
        }else {
            return R.error("信息不存在");
        }
    }

    /**
     * 获取已经登录用户菜单信息
     * @return
     */
    @Operation(summary = "获取用户菜单",description = "已经登录的用户获取菜单信息")
    @GetMapping("/user/menu")
    public R<List<SysMenuDto>> getMenu(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new TokenAuthenticationException("token异常");
        }
        SecurityUserDetails securityUserDetails = (SecurityUserDetails) redisUserCache.getUserFromCache(authentication.getPrincipal().toString());
        if (securityUserDetails != null){
            return R.ok(securityUserDetails.getSysMenuList());
        }
        return R.error("信息不存在");
    }

}
