package com.coffee.system.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 系统角色表
 * @author rabit
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class SysRole {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String sysRoleName;
    private String sysRoleCode;
    private String path;
    private String sysRoleDesc;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Timestamp updateTime;
    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer deleted;
}
