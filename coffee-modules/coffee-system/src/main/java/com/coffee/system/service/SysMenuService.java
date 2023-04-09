package com.coffee.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.system.dto.SysMenuDto;
import com.coffee.system.model.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @author rabit
 */
public interface SysMenuService extends IService<SysMenu> {


    PageData<SysMenu> getPageMenu(PageInfo page, SysMenu menu) ;

    /**
     * 通过角色id获取菜单
     * @param roleIds 角色id
     * @return 菜单信息
     */
    List<SysMenu> getSysMenuByRoleId(Set<Long> roleIds);

    /**
     * 通过url获取菜单信息
     * @param url url信息
     * @return 返回菜单权限
     */
    List<SysMenu> getSysMenuByUrl(String url);

    /**
     * 通过用户id联表查询用户id
     * @param uid 用户id
     * @return 菜单列表
     */
    List<SysMenuDto> getSysMenuByUserId(Long uid);

}
