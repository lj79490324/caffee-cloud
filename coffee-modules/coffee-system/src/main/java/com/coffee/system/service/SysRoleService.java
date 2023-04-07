package com.coffee.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee.system.model.SysRole;


import java.util.LinkedHashSet;
import java.util.List;

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

    LinkedHashSet<SysRole> getSysRoleListByUserId(Long userId);
}
