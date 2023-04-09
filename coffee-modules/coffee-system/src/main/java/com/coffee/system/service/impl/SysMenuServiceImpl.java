package com.coffee.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.system.dto.SysMenuDto;
import com.coffee.system.mapper.SysMenuMapper;
import com.coffee.system.model.SysMenu;
import com.coffee.system.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 菜单服务
 * @author rabit
 * @version v1.0
 * @date 2022/9/1 22:35
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public PageData<SysMenu> getPageMenu(PageInfo page, SysMenu menu) {
        //模糊查询需要重新编写
        Page<SysMenu> sysMenuPage = baseMapper.selectPage(page.covertParam(), new QueryWrapper<>(menu));
        return PageData.covertData(sysMenuPage);
    }

    @Override
    public List<SysMenu> getSysMenuByRoleId(Set<Long> roleIds) {
        return baseMapper.getSysMenuByRoleId(roleIds);
    }

    @Override
    public List<SysMenu> getSysMenuByUrl(String url) {
        //如果有缓存，先从缓存获取
        QueryWrapper<SysMenu> qw = new QueryWrapper<>();
        qw.eq("sys_menu_url", url);
        return baseMapper.selectList(qw);
    }

    @Override
    public List<SysMenuDto> getSysMenuByUserId(Long uid) {
        return baseMapper.getSysMenuByUserId(uid);
    }

    @Override
    public boolean save(SysMenu entity) {
        //如果authCode为空，需要手动添加
        if (!StringUtils.hasLength(entity.getAuthCode())){
           entity.setAuthCode(entity.getSysMenuCode()+"_code");
        }
        SysMenu pSysMenu = baseMapper.selectById(entity.getParentId());
        //判断上级文件夹的关系
        if ((entity.getSysMenuType() ==2 && pSysMenu.getSysMenuType() != 0) || (Objects.nonNull(entity.getParentId()) && pSysMenu.getSysMenuType() != 1)){
                return false;
        }
        if (pSysMenu.getSysMenuType() != 2){
            super.save(entity);
            entity.setPath(pSysMenu.getPath()+"_"+entity.getId());
            return super.updateById(entity);
        }
        return false;
    }

    @Override
    public boolean updateById(SysMenu entity) {
        if (entity.getId() == null) return false;
        SysMenu currentMenu = super.getById(entity.getId());
        //判断是否是更新父级菜单
        if (!Objects.equals(currentMenu.getParentId(), entity.getParentId())){
            SysMenu pSysMenu = baseMapper.selectById(entity.getParentId());
            //此处需要判断上级文件夹的关系
            if (pSysMenu.getSysMenuType()== 2||(entity.getSysMenuType() ==2 && pSysMenu.getSysMenuType() != 0) || (Objects.nonNull(entity.getParentId()) && pSysMenu.getSysMenuType() != 1)){
                return false;
            }
        }
        return super.updateById(entity);
    }
}
