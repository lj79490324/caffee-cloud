package com.coffee.system.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.coffee.common.core.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 权限菜单表
 * @author rabit
 */
@Schema(description = "权限菜单实体")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenu extends BaseEntity implements Serializable {
    @Schema(description = "菜单id",minimum = "2",requiredMode = Schema.RequiredMode.AUTO,example = "2")
    @TableId(type = IdType.AUTO)
    private Long id;
    @Schema(description = "父级菜单id",minimum = "1",requiredMode = Schema.RequiredMode.AUTO,example = "2")
    private Long parentId;
    @Schema(description = "菜单名称",requiredMode = Schema.RequiredMode.REQUIRED,example = "系统设置")
    private String sysMenuName;
    @Schema(description = "菜单编码",example = "sys_setting")
    private String sysMenuCode;
    @Schema(description = "菜单地址")
    private String sysMenuUrl;
    @Schema(description = "前端请求方法",allowableValues = {"GET","POST","PUT","DELETE"},requiredMode = Schema.RequiredMode.AUTO,example = "GET")
    private String sysMenuUrlAction;
    @Schema(description = "前端组件路径")
    private String sysMenuComponent;
    @Schema(description = "菜单类型（0页面、1文件夹，2按钮,3外部链接(本窗口打开)，4外部链接，新窗口打开）",allowableValues = {"0","1","2","3","4"},defaultValue = "0",example = "0")
    private Integer sysMenuType;
    @Schema(description = "菜单图标")
    private String sysMenuIcon;
    @Schema(description = "菜单排序")
    private Integer sysMenuOrder;
    @Schema(description = "数据路径")
    private String path;
    @Schema(description = "菜单描述")
    private String sysMenuDesc;
    @Schema(description = "菜单状态(0启用、1禁用.2接口异常)",allowableValues = {"0","1","2"})
    private Integer status;
    @Schema(description = "是否显示（0显示,1不显示）",requiredMode = Schema.RequiredMode.REQUIRED,allowableValues = {"0","1"})
    private Integer display;
    @Schema(description = "系统设置权限编码(唯一)",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String authCode;

}
