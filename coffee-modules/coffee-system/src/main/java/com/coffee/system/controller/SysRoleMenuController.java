package com.coffee.system.controller;

import com.coffee.common.core.R;
import com.coffee.system.dto.SysRoleMenuDto;
import com.coffee.system.model.SysRole;
import com.coffee.system.model.SysRoleMenu;
import com.coffee.system.service.SysRoleMenuService;
import com.coffee.system.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController{

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 根据子id获取权限，获取的权限包括父id权限及子id的权限
     * @return 返回列表结果
     */
    @GetMapping("{rid}")
    public R<Map<String, List<SysRoleMenuDto>>> getAuthorityByRoleId(@PathVariable("rid") Long rid){
        SysRole sysRole = sysRoleService.getById(rid);
        if (sysRole!=null){
            List<SysRoleMenuDto> childSysRoleMenuList = sysRoleMenuService.getSysRoleMenuDtoByRid(rid);
            List<SysRoleMenuDto> parentSysRoleMenuList = sysRoleMenuService.getSysRoleMenuDtoByRid(sysRole.getParentId());
            Map<String,List<SysRoleMenuDto>> res= new HashMap<>(16);
            res.put("parent",parentSysRoleMenuList);
            res.put("child",childSysRoleMenuList);
            return R.ok(res);
        }else {
            return R.error("角色不存在");
        }
    }

    /**
     * 授权操作
     * @param list 授权对象
     * @return true 成功 false失败
     */
    @PostMapping
    public R<Boolean> grantAuthority(@RequestBody List<SysRoleMenu> list){
        log.info(list.toString());
        return R.ok(true);
    }
}
