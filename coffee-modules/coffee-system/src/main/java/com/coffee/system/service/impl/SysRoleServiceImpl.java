package com.coffee.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.coffee.system.mapper.SysRoleMapper;
import com.coffee.system.model.SysRole;
import com.coffee.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public LinkedHashSet<SysRole> getSysRoleListByUserId(Long userId) {
        LinkedHashSet<SysRole> set = new LinkedHashSet<>();
        List<SysRole> list = baseMapper.getSysRoleListByUserId(userId);
        set.addAll(list);
        list.forEach(item-> {
             List<SysRole> tmpLists = baseMapper.selectList(new QueryWrapper<SysRole>().likeRight("path", item.getPath()));
             set.addAll(tmpLists);
        });
        return set;
    }

    @Override
    public boolean saveOrUpdate(SysRole entity) {
        boolean flag = false;
        if (entity.getId() == null){
            //id不存在，保存操作
            if (super.save(entity)){
                SysRole sysRole = baseMapper.selectById(entity.getParentId());
                if (sysRole != null){
                    entity.setPath(sysRole.getPath()+"_"+entity.getId());
                }else {
                    entity.setPath("1_"+entity.getPath());
                }
                if (super.updateById(entity)){
                    flag = true;
                }
            }
        }else {
            //id存在，更新操作
            //父id和code不允许更新,否则会打乱原有权限树
            entity.setSysRoleCode(null);
            entity.setParentId(null);
            entity.setPath(null);
            flag = updateById(entity);
        }
        return flag;
    }

}
