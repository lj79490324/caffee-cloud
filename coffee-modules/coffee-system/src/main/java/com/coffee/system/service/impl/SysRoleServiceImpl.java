package com.coffee.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.coffee.system.mapper.SysRoleMapper;
import com.coffee.system.model.SysRole;
import com.coffee.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色service
 * @author rabit
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Override
    public List<Long> getSysRoleByUserId(Long userId) {
        return baseMapper.getSysRoleByUserId(userId);
    }

    @Override
    public List<Long> getSysRoleIdByGroupUser(Long userId) {
        return baseMapper.getSysRoleIdByGroupUser(userId);
    }

    @Override
    public boolean save(SysRole entity) {
        boolean flag = false;
        if (super.save(entity)){
            SysRole sysRole = baseMapper.selectById(entity.getParentId());
            if (sysRole != null){
                entity.setSysRoleCode(sysRole.getPath()+"_"+entity.getId());
            }
            if (super.updateById(entity)){
                flag = true;
            }
        }
        return flag;
    }
}
