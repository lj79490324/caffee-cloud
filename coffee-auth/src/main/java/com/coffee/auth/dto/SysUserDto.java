package com.coffee.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;


@Schema(description = "用户信息")
@Data
@Builder
public class SysUserDto{

    @Schema(description = "用户名",accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;
    @Schema(description = "用户头像",accessMode = Schema.AccessMode.READ_ONLY)
    private String photo;
    @Schema(description = "最后登录时间",accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime time;
    @Schema(description = "角色列表",accessMode = Schema.AccessMode.READ_ONLY)
    private List<String> roles;
    @Schema(description = "权限编码列表",accessMode = Schema.AccessMode.READ_ONLY)
    private List<String> authBtnList;
}
