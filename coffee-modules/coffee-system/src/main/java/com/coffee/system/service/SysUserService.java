package com.coffee.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.system.model.SysMenu;
import com.coffee.system.model.SysUser;

/**
 * @author rabit
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 用户登录信息查询
     * @param userName  用户名
     * @return 用户实体
     */
    SysUser queryByUsername(String userName);

    PageData<SysUser> getPageUser(PageInfo page, SysUser sysUser) ;
}
