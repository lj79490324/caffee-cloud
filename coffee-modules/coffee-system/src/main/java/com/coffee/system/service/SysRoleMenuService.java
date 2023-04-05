package com.coffee.system.service;

import com.coffee.system.dto.SysRoleMenuDto;
import com.coffee.system.model.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<SysRoleMenuDto> getSysRoleMenuDtoByRid(Long rid);
}
