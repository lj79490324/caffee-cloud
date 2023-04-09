package com.coffee.system.controller;

import com.coffee.system.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 系统参数配置
 * @author rabit
 * @version v1.0
 * @date 2022/9/12 0:03
 */
@RestController
@RequestMapping("sysParam")
public class SysParamController {

    @Autowired
    private SysParamService sysParamService;



}
