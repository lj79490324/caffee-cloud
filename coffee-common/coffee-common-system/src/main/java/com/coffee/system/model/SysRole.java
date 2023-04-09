package com.coffee.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.coffee.common.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统角色表
 * @author rabit
 */
@Schema(description = "系统角色对象")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRole extends BaseEntity {
    @Schema(description = "角色id",minimum = "2",requiredMode = Schema.RequiredMode.AUTO,example = "2")
    @TableId(type = IdType.AUTO)
    private Long id;
    @Schema(description = "父级角色id",minimum = "1",requiredMode = Schema.RequiredMode.AUTO,example = "2")
    private Long parentId;
    @Schema(description = "角色名称（全局唯一）",requiredMode = Schema.RequiredMode.REQUIRED,example = "超级管理员")
    private String sysRoleName;
    @Schema(description = "角色编码（全局唯一）",requiredMode = Schema.RequiredMode.REQUIRED,example = "super_admin")
    private String sysRoleCode;
    @Schema(description = "角色数据路径")
    @JsonIgnore
    private String path;
    @Schema(description = "角色描述")
    private String sysRoleDesc;
    @Schema(description = "角色状态(0:启用,1:禁用)",allowableValues = {"0","1"},defaultValue = "0")
    private Integer status;
}
