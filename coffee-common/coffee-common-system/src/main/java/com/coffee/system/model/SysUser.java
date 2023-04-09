package com.coffee.system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rabit
 */
@Schema(description = "系统用户")
@Data
public class SysUser implements Serializable {


    private static final long serialVersionUID = 1L;
    @Schema(description = "用户id",requiredMode = Schema.RequiredMode.AUTO,example = "2")
    private Long id;
    @Schema(description = "用户登录名(全局唯一，且不可修改)",requiredMode = Schema.RequiredMode.REQUIRED,example = "2")
    private String username;
    @Schema(description = "用户登录密码",requiredMode = Schema.RequiredMode.AUTO)
    private String password;
    @Schema(description = "详情id(预留字段)",requiredMode = Schema.RequiredMode.NOT_REQUIRED,hidden = true)
    private Long detailsId;
    @Schema(description = "用户编码",requiredMode = Schema.RequiredMode.NOT_REQUIRED,hidden = true)
    private String sysCode;
    @Schema(description = "用户昵称")
    private String nickName;
    @Schema(description = "最后登录时间",format = "yyyy:MM:DD hh:mm:ss")
    private LocalDateTime lastLoginTime;
    @Schema(description = "登录次数")
    private Long loginCount;
    @Schema(description = "账号状态（4已锁定，3过期，2异常，1禁止登录，0正常）",allowableValues = {"0","1","2","3","4"},defaultValue = "0")
    private Integer status;


}
