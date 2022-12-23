package com.coffee.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffee.system.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表
 * @author rabit
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户id获取角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    List<Long> getSysRoleByUserId(@Param("userId") Long userId);

    /**
     * 使用用户id通过用户组查询角色信息
     * @param userId 用户id
     * @return 角色id
     */
    List<Long> getSysRoleIdByGroupUser(@Param("userId") Long userId);
}
