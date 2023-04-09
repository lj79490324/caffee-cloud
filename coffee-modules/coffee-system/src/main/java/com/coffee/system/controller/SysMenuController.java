package com.coffee.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.common.core.R;
import com.coffee.system.dto.SysMenuDto;
import com.coffee.system.model.SysMenu;
import com.coffee.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统菜单")
@Slf4j
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping("get")
    public R<List<SysMenu>> getAllMenu(){
        return R.ok(sysMenuService.list(new QueryWrapper<SysMenu>(null).orderByAsc("sys_menu_order")));
    }
    @GetMapping("/{id}")
    public R<SysMenu> getOne(@PathVariable("id") Integer id){
        return R.ok(sysMenuService.getById(id));
    }

    @GetMapping("url")
    public R<List<SysMenu>> getSysMenuByUrl(@RequestParam("url") String url) {
        return R.ok(sysMenuService.getSysMenuByUrl(url));
    }

    @RequestMapping("page")
    public R<PageData<SysMenu>> getPageMenu(PageInfo page, SysMenu menu){
        System.out.println(page);
        return R.ok(sysMenuService.getPageMenu(page,menu));
    }


    @GetMapping("/all/{userId}")
    public R<List<SysMenuDto>> getMenuByUserId(@PathVariable("userId") Long userId){
        return R.ok(sysMenuService.getSysMenuByUserId(userId));
    }

    @PostMapping
    public R<?> saveOrUpdate(@RequestBody SysMenu sysMenu){
        System.out.println(sysMenu);

        if (sysMenu.getId() == null){
            return R.ok(sysMenuService.save(sysMenu));
        }
        return R.ok(sysMenuService.updateById(sysMenu));
    }

}
