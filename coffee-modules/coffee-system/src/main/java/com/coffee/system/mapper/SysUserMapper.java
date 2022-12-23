package com.coffee.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffee.system.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author rabit
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户信息
     * @param userName  用户名
     * @return 用户信息
     */
    SysUser queryByUsername(@Param("userName") String userName);
}
