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
 * 用户角色表
 * @TableName sys_user_role_conn
 */
@TableName(value ="sys_user_role_conn")
@Data
public class SysUserRoleConn implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     *
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态（正常、禁用）
     */
    private Integer status;
    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
