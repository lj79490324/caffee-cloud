package com.coffee.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.common.core.R;
import com.coffee.system.dto.SysMenuDto;
import com.coffee.system.model.SysMenu;
import com.coffee.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统菜单模块",description = "菜单相关操作")
@Slf4j
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取所有的菜单信息
     * @return 返回所有菜单列表，已列表形式返回
     */
    @Operation(summary = "获取所有的菜单信息",description = "不带分页查询所有的菜单")
    @GetMapping("get")
    public R<List<SysMenu>> getAllMenu(){
        return R.ok(sysMenuService.list(new QueryWrapper<SysMenu>(null).orderByAsc("sys_menu_order")));
    }

    /**
     * 通过菜单id查询单个菜单
     * @param id 菜单id
     * @return 返回菜单实体，如果不存在返回null
     */
    @Operation(summary = "查询单个菜单",description = "通过菜单id查询单个菜单")
    @Parameter(name = "id",description = "菜单id",in = ParameterIn.PATH)
    @GetMapping("/{id}")
    public R<SysMenu> getOne(@PathVariable("id") Integer id){
        return R.ok(sysMenuService.getById(id));
    }

    /**
     * 分页查询菜单
     * @param page 分页参数和关键字
     * @param menu 菜单相关字典
     * @return 菜单列表
     */
    @Operation(summary = "分页查询菜单",description = "通过菜单信息，分页查询菜单信息")
    @PostMapping("page")
    public R<PageData<SysMenu>> getPageMenu(PageInfo page, SysMenu menu){
        return R.ok(sysMenuService.getPageMenu(page,menu));
    }

    /**
     * 通过用户id关联查询菜单信息,用户必须登录才可以进行查询
     * @param userId 用户id
     * @return 菜单列表
     */
    @Operation(summary = "用户id查询菜单",description = "通过用户id查询菜单，用户必须登录")
    @GetMapping("/all/{userId}")
    public R<List<SysMenuDto>> getMenuByUserId(@PathVariable("userId") Long userId){
        return R.ok(sysMenuService.getSysMenuByUserId(userId));
    }


    /**
     * 保存或者更新菜单，如果id存在更新信息，如果id不存在则保存信息
     * @param sysMenu 菜单信息
     * @return 返回是否操作成功
     */
    @Operation(summary = "保存|更新菜单",description = "如果id存在更新信息，如果id不存在则保存信息")
    @PostMapping
    public R<?> saveOrUpdate(@RequestBody SysMenu sysMenu){
        if (sysMenu.getId() == null){
            return R.ok(sysMenuService.save(sysMenu));
        }
        return R.ok(sysMenuService.updateById(sysMenu));
    }

}
