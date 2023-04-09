package com.coffee.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coffee.common.core.R;
import com.coffee.common.core.utils.JwtUtil;
import com.coffee.common.redis.service.RedisService;
import com.coffee.system.model.SysRole;
import com.coffee.system.model.SysUser;
import com.coffee.system.service.SysRoleService;
import com.coffee.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "系统角色模块")
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
     * @param token 请求头中的参数
     * @return 返回角色的列表，去重
     */
    @Operation(summary = "token获取角色列表",description = "通过token获取关联的角色列表，列表不重复")
    @GetMapping("get")
    public R<LinkedHashSet<SysRole>> getAllRole(@RequestHeader("Authorization") String token){
        log.error(token);
        if (StringUtils.isEmpty(token)){
            return R.error("请求头错误");
        }

        if ( token.startsWith(JwtUtil.TOKEN_PREFIX)){
            token = token.substring(JwtUtil.TOKEN_PREFIX.length()).trim();
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
    @Operation(summary = "查询单个角色",description = "通过菜单id查询单个角色")
    @Parameter(name = "id",description = "角色id",in = ParameterIn.PATH)
    @GetMapping("/{id}")
    public R<SysRole> getOne(@PathVariable("id") Integer id){
        return R.ok(sysRoleService.getById(id));
    }
    /**
     * 校验角色名和角色编码是否存在
     * @param sysRole 角色参数
     * @return true: 不存在    false：存在
     */
    @Operation(summary = "校验角色信息",description = "通过参数校验角色名和角色编码是否存在")
    @PostMapping("validate")
    public R<Boolean> validate(@RequestBody SysRole sysRole){
        long count = sysRoleService.count(new QueryWrapper<SysRole>(sysRole));
        if (count > 0){
           return R.ok(false);
        }
        return R.ok(true);
    }

    @Operation(summary = "保存|更新菜单",description = "如果id存在更新信息，如果id不存在则保存信息")
    @PostMapping
    public R<Boolean> saveOrUpdate(@RequestBody SysRole sysRole){
        return R.ok(sysRoleService.saveOrUpdate(sysRole));
    }
}
