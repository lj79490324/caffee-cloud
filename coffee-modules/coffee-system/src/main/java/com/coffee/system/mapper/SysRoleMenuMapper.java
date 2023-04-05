package com.coffee.system.mapper;

import com.coffee.system.dto.SysRoleMenuDto;
import com.coffee.system.model.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity com.coffee.system.model.SysRoleMenu
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<SysRoleMenuDto> getSysRoleMenuDtoByRid(Long rid);
}




