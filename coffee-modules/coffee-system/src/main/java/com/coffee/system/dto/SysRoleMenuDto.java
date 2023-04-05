package com.coffee.system.dto;

import com.coffee.system.model.SysRoleMenu;
import lombok.Data;

@Data
public class SysRoleMenuDto extends SysRoleMenu {
    private Long parentId;
    private String sysMenuName;
    private String sysMenuIcon;
}
