package com.coffee.system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class SysMenu implements Serializable {
    private Long id;
    private Long parentId;
    private String sysMenuName;
    private String sysMenuCode;
    private String sysMenuUrl;
    private String sysMenuComponent;
    private Integer sysMenuType;
    private String sysMenuIcon;
    private Integer sysMenuOrder;
    private String path;
    private String sysMenuDesc;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;
    private String authCode;
     /**
     * 是否删除
     */
     @JsonIgnore
    private Integer deleted;
}
