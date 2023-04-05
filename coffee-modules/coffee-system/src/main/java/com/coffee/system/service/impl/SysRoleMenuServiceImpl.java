package com.coffee.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.system.dto.SysRoleMenuDto;
import com.coffee.system.model.SysRoleMenu;
import com.coffee.system.service.SysRoleMenuService;
import com.coffee.system.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService{

    @Override
    public List<SysRoleMenuDto> getSysRoleMenuDtoByRid(Long rid) {
        return baseMapper.getSysRoleMenuDtoByRid(rid);
    }
}




