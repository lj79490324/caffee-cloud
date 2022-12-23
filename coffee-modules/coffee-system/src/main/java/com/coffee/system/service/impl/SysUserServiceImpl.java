package com.coffee.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.common.core.PageData;
import com.coffee.common.core.PageInfo;
import com.coffee.system.mapper.SysUserMapper;
import com.coffee.system.model.SysMenu;
import com.coffee.system.model.SysUser;
import com.coffee.system.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rabit
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public SysUser queryByUsername(String userName) {
        return baseMapper.queryByUsername(userName);
    }

    @Override
    public PageData<SysUser> getPageUser(PageInfo page, SysUser sysUser) {
        //模糊查询需要重新编写
        Page<SysUser> sysMenuPage = baseMapper.selectPage(page.covertParam(), new QueryWrapper<>(sysUser));
        sysMenuPage.getRecords().forEach(item -> item.setPassword(""));
        return PageData.covertData(sysMenuPage);
    }
}
