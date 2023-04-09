package com.coffee.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统参数表
 * @TableName sys_param
 */
@TableName(value ="sys_param")
@Data
public class SysParam implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 参数名
     */
    private String sysParamName;

    /**
     * 参数编码
     */
    private String sysParamCode;

    /**
     * 参数值
     */
    private String sysParamValue;

    /**
     * 参数默认值
     */
    private String sysParamDefault;

    /**
     * 参数描述
     */
    private String sysParamDes;

    /**
     * 参数状态（启用、禁用）
     */
    private Integer status;

    /**
     * 参数创建时间
     */
    private LocalDateTime createTime;

    /**
     * 参数更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
