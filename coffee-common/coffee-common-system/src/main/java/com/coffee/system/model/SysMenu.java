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
@Schema(description = "菜单实体")
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
    @Schema(description = "上次更新时间",format = "")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;
    @Schema(description = "上次更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Timestamp updateTime;
    @Schema(description = "菜单状态(0启用、1禁用.2接口异常)",allowableValues = {"0","1","2"})
    private Integer status;
    @Schema(description = "是否显示（0显示,1不显示）",allowableValues = {"0","1"})
    private Integer display;
    @Schema(description = "系统设置权限编码(唯一)",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String authCode;
    @Schema(description = "客户端设置权限编码(唯一)",requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(exist = false)
    private String sysRoleCode;
     /**
     * 是否删除
     */
     @JsonIgnore
    private Integer deleted;
}
