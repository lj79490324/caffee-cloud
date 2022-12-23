package com.coffee.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coffee.common.core.R;
import com.coffee.system.model.SysRole;
import com.coffee.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
