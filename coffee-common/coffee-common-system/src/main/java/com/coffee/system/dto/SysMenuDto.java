package com.coffee.system.dto;

import com.coffee.system.model.SysMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "菜单实体DTO")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuDto extends SysMenu {
    @Schema(description = "客户端设置权限编码(唯一)",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String sysRoleCode;
}
