package com.coffee.system.controller;

import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.common.core.R;
import com.coffee.system.model.SysMenu;
import com.coffee.system.model.SysUser;
import com.coffee.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/info/{userName}")
    public R<SysUser> getUserInfo(@PathVariable("userName") String userName){
        SysUser sysUser = sysUserService.queryByUsername(userName);
        return R.ok(sysUser);
    }
    @PostMapping("page")
    public R<PageData<SysUser>> getPageMenu(PageInfo page, SysUser sysUser){
        System.out.println(page);
        return R.ok(sysUserService.getPageUser(page,sysUser));
    }
}
