package com.coffee.auth.security.model;

import com.coffee.system.model.SysMenu;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class SecurityUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    // 4已锁定，3过期，2异常，1禁止登录，0正常
    private Integer status;
    //用户菜单
    private List<SysMenu> sysMenuList;

    private HashSet<String> roles;

    private HashSet<String> authBtnList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status != 3;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status != 4;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status != 2;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SysMenu> getSysMenuList() {
        return sysMenuList;
    }

    public void setSysMenuList(List<SysMenu> sysMenuList) {
        this.sysMenuList = sysMenuList;
    }

    public HashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<String> roles) {
        this.roles = roles;
    }

    public HashSet<String> getAuthBtnList() {
        return authBtnList;
    }

    public void setAuthBtnList(HashSet<String> authBtnList) {
        this.authBtnList = authBtnList;
    }
}
