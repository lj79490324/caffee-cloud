package com.coffee.system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rabit
 */
@Data
public class SysUser implements Serializable {


    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private Long detailsId;
    private String sysCode;
    private String nickName;
    private LocalDateTime lastLoginTime;
    private Long loginCount;
    private LocalDateTime createTime;
    private Integer status;
    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer deleted;


}
