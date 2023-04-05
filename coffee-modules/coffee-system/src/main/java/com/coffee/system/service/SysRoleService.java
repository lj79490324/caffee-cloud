package com.coffee.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee.system.model.SysRole;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

/**
 * 用户角色
 * @author rabit
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 通过用户id获取角色列表
     * @param userId 用户id
     * @return 用户角色列表
     */
    List<Long> getSysRoleByUserId(Long userId);

    /**
     * 使用用户id通过用户组查询角色信息
     * @param userId 用户id
     * @return 角色id
     */
    List<Long> getSysRoleIdByGroupUser(Long userId);

    List<SysRole> getSysRoleByUserInfo(Map<String,Object> param);
}
