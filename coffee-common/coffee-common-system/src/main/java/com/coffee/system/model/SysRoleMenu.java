package com.coffee.system.model;

import com.baomidou.mybatisplus.annotation.*;
import com.coffee.common.core.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色权限表
 * @TableName sys_role_menu
 */

@Schema(description = "角色权限关联实体")
@TableName(value ="sys_role_menu")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleMenu extends BaseEntity implements Serializable {
    /**
     * 标识id
     */
    @Schema(description = "关联id",minimum = "1",requiredMode = Schema.RequiredMode.AUTO,example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色id
     */
    @Schema(description = "角色id",minimum = "2",requiredMode = Schema.RequiredMode.REQUIRED,example = "2")
    private Long roleId;

    /**
     * 菜单id
     */
    @Schema(description = "菜单id",minimum = "2",requiredMode = Schema.RequiredMode.REQUIRED,example = "2")
    private Long menuId;

    /**
     * 角色权限类型（可授权、可访问）
     */
    @Schema(description = "角色权限类型（0可访问,1可授权）",requiredMode = Schema.RequiredMode.REQUIRED,defaultValue = "0",example = "0")
    private Integer roleMenuType;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
