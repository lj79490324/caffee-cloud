package com.coffee.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffee.system.model.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 通过角色id获取菜单
     * @param roleIds 角色id
     * @return 菜单信息
     */
    List<SysMenu> getSysMenuByRoleId(Set<Long> roleIds);

    List<SysMenu> getSysMenuByUserId(@Param("uid")Long uid);
}
