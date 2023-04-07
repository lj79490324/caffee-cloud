package com.coffee.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coffee.common.core.R;
import com.coffee.common.core.utils.JwtUtil;
import com.coffee.common.redis.service.RedisService;
import com.coffee.system.model.SysRole;
import com.coffee.system.model.SysUser;
import com.coffee.system.service.SysRoleService;
import com.coffee.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;
    /**
     * 通过token获取当前用户的用户名，然后通过用户名获取用户的角色及对应的子角色
     * @param token
     * @return
     */
    @GetMapping("get")
    public R<LinkedHashSet<SysRole>> getAllRole(@RequestHeader("Authorization") String token){
        log.error(token);
        if (StringUtils.isEmpty(token)){
            return R.error("请求头错误");
        }
        if (token.indexOf(JwtUtil.TOKEN_PREFIX) > 0){
            token = token.substring(JwtUtil.TOKEN_PREFIX.length());
        }
        //从token中获取用户名称
        String username = JwtUtil.getUsername(token);
        log.error("userName={}",username);
        //如果是超管则获取所有的角色
        SysUser sysUser = sysUserService.queryByUsername(username);
        if (sysUser == null){
            return R.error("请求头错误");
        }
        return R.ok(sysRoleService.getSysRoleListByUserId(sysUser.getId()));
    }


    /**
     * 通过idh
     * @param id 获取角色
     * @return 单个角色信息
     */
    @GetMapping("/{id}")
    public R<SysRole> getOne(@PathVariable("id") Integer id){
        return R.ok(sysRoleService.getById(id));
    }
    /**
     * 校验角色名和角色编码是否存在
     * @param sysRole 角色参数
     * @return true: 不存在    false：存在
     */
    @PostMapping("validate")
    public R<Boolean> validate(@RequestBody SysRole sysRole){
        long count = sysRoleService.count(new QueryWrapper<SysRole>(sysRole));
        if (count > 0){
           return R.ok(false);
        }
        return R.ok(true);
    }


    @PostMapping
    public R<Boolean> saveOrUpdate(@RequestBody SysRole sysRole){
        return R.ok(sysRoleService.saveOrUpdate(sysRole));
    }
}
