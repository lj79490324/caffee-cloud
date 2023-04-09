package com.coffee.system.dto;

import com.coffee.system.model.SysRoleMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权的dto 帮助返回菜单名称和属性菜单
 * @author rabit
 */
@Schema(description = "角色授权DTO")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleMenuDto extends SysRoleMenu {
    @Schema(description = "父级菜单id")
    private Long parentId;
    @Schema(description = "菜单名称")
    private String sysMenuName;
    @Schema(description = "菜单icon")
    private String sysMenuIcon;
}
