package com.coffee.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coffee.common.core.R;
import com.coffee.system.model.SysRole;
import com.coffee.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;


    @GetMapping("get")
    public R<List<SysRole>> getAllRole(){
        return R.ok(sysRoleService.list(new QueryWrapper<SysRole>(null)));
    }


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
