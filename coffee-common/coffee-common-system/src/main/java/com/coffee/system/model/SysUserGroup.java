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
 * 用户组表
 * @TableName sys_user_group
 */
@TableName(value ="sys_user_group")
@Data
public class SysUserGroup implements Serializable {
    /**
     * 组id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父组id
     */
    private Integer parentId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 组编码
     */
    private String groupCode;

    /**
     * 数据路径
     */
    private String path;

    /**
     * 组描述
     */
    private String groupDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 组状态
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
