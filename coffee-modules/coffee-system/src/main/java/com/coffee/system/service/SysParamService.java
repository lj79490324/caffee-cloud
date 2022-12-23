package com.coffee.system.service;

import com.coffee.system.model.SysParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface SysParamService extends IService<SysParam> {

    /**
     * 加载缓存
     */
     void loadParamCache();

    /**
     * 清空系统所有的参数
     */
    void cleanParamCache();

    /**
     * 更新系统参数值
     * @param sysParam 系统参数
     * @return 返回更新的条数
     */
    int updateParam(SysParam sysParam);

    /**
     * 增加系统参数
     * @param sysParam 系统参数
     * @return 返回增加的条数
     */
    int insertParam(SysParam sysParam);


}
