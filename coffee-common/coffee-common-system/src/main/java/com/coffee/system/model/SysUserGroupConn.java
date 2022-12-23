package com.coffee.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户组关联表
 * @TableName sys_user_group_conn
 */
@TableName(value ="sys_user_group_conn")
@Data
public class SysUserGroupConn implements Serializable {
    /**
     *
     */
    @TableId
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 组id
     */
    private Integer groupId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态
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
