package com.coffee.auth.service;

import com.coffee.auth.feign.RouterSysMenuService;
import com.coffee.auth.feign.RouterUserService;
import com.coffee.auth.security.model.SecurityUserDetails;
import com.coffee.common.core.Constant;
import com.coffee.common.core.R;
import com.coffee.system.model.SysMenu;
import com.coffee.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private RouterUserService routerUserService;

    @Autowired
    private RouterSysMenuService routerSysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<SysUser> userInfo = routerUserService.getUserInfo(username);
        SysUser data = userInfo.getData();
        if (data == null || userInfo.getCode() != Constant.OK_CODE){
            throw new UsernameNotFoundException("用户名不存在");
        }
        SecurityUserDetails userDetails = new SecurityUserDetails();
        userDetails.setUsername(data.getUsername());
        userDetails.setPassword(data.getPassword());
        userDetails.setEnabled(true);
        List<SysMenu> sysMenuList = loadSysMenuByUserId(data.getId());
        userDetails.setSysMenuList(sysMenuList);
        userDetails.setStatus(data.getStatus());
        return userDetails;
    }

    private List<SysMenu> loadSysMenuByUserId(Long uid){
        R<List<SysMenu>> res = routerSysMenuService.getMenuByUserId(uid);
        return res.getData();
    }

}
