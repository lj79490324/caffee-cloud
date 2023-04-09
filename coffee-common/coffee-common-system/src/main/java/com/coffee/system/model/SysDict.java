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
 * 系统字典表
 * @TableName sys_dict
 */
@TableName(value ="sys_dict")
@Data
public class SysDict implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 字典名
     */
    private String sysDictName;

    /**
     * 参数编码
     */
    private String sysDictCode;

    /**
     * 参数值
     */
    private String sysDictValue;

    /**
     * 参数显示值
     */
    private String sysDictDisplayValue;

    /**
     * 是否是默认
     */
    private Integer sysDictIsDefault;

    /**
     * 参数描述
     */
    private String sysDictDesc;

    /**
     * 参数状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
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
