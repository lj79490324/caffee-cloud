package com.coffee.system.model;

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
    private Long id;
    private Long parentId;
    private String sysRoleName;
    private String sysRoleCode;
    private String path;
    private String sysRoleDesc;
    private Integer status;
    private Timestamp createTime;
    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer deleted;
}
