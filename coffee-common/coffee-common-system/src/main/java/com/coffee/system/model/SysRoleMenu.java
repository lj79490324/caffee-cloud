package com.coffee.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限表
 * @TableName sys_role_menu
 */
@TableName(value ="sys_role_menu")
@Data
public class SysRoleMenu implements Serializable {
    /**
     * 标识id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

    /**
     * 角色权限类型（可授权、可访问）
     */
    private Integer roleMenuType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
