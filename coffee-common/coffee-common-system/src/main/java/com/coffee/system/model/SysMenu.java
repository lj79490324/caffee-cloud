package com.coffee.system.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 权限菜单表
 * @author rabit
 */
@Data
public class SysMenu implements Serializable {
    @Schema(description = "菜单id",minimum = "1",requiredMode = Schema.RequiredMode.AUTO)
    @TableId(type = IdType.AUTO)
    private Long id;
    @Schema(description = "父级菜单id",minimum = "1",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long parentId;
    @Schema(description = "菜单名称",minimum = "1",requiredMode = Schema.RequiredMode.REQUIRED)
    private String sysMenuName;
    private String sysMenuCode;
    private String sysMenuUrl;
    private String sysMenuUrlAction;
    private String sysMenuComponent;
    private Integer sysMenuType;
    private String sysMenuIcon;
    private Integer sysMenuOrder;
    private String path;
    private String sysMenuDesc;
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Timestamp updateTime;
    private Integer status;
    private Integer display;
    private String authCode;
    @TableField(exist = false)
    private String sysRoleCode;
     /**
     * 是否删除
     */
     @JsonIgnore
    private Integer deleted;
}
