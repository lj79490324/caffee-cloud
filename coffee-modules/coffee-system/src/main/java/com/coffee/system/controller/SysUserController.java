package com.coffee.system.controller;

import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.common.core.R;
import com.coffee.system.model.SysMenu;
import com.coffee.system.model.SysUser;
import com.coffee.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "系统用户模块",description = "系统用户模块")
@Slf4j
@RestController
@RequestMapping("sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名（唯一）
     * @return 返回用户信息
     */
    @Operation(summary = "用户名查询用户",description = "通过用户名查询用户信息")
    @Parameter(name = "userName",description = "用户登录名",in = ParameterIn.PATH)
    @GetMapping("/info/{userName}")
    public R<SysUser> getUserInfo(@PathVariable("userName") String userName){
        SysUser sysUser = sysUserService.queryByUsername(userName);
        return R.ok(sysUser);
    }

    /**
     * 分页查询系统用户信息
     * @param page 分页参数和关键字
     * @param sysUser 系统用户关键字
     * @return 用户列表
     */
    @Operation(summary = "分页查询用户",description = "通过用户信息，分页查询用户信息")
    @PostMapping("page")
    public R<PageData<SysUser>> getPageMenu(PageInfo page,SysUser sysUser){
        System.out.println(page);
        return R.ok(sysUserService.getPageUser(page,sysUser));
    }
}
