package com.coffee.auth.dto;

import lombok.Builder;
import lombok.Data;


import java.util.Date;
import java.util.List;


@Data
@Builder
public class SysUserDto{
    private String userName;
    private String photo;
    private Date time;
    private List<String> roles;

    private List<String> authBtnList;
}
