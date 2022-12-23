package com.coffee.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.common.core.SysCacheConstant;
import com.coffee.common.redis.service.RedisService;
import com.coffee.system.model.SysParam;
import com.coffee.system.service.SysParamService;
import com.coffee.system.mapper.SysParamMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 数据库参数配置
 *
 * @author rabit
 * @version v1.0
 * @date 2022/9/12 0:09
 */
@Slf4j
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void initData() {
        log.info("--------参数数据开始配置start------");
        loadParamCache();
        log.info("--------参数数据开始配置end------");
    }

    @Override
    public void loadParamCache() {
        List<SysParam> sysParams = baseMapper.selectList(null);
        sysParams.forEach(item ->{
            redisService.set(SysCacheConstant.PARAM_CACHE_KEY_PREFIX+item.getSysParamCode(),item.getSysParamValue());
        });
    }

    @Override
    public void cleanParamCache() {
        redisService.del(SysCacheConstant.PARAM_CACHE_KEY_PREFIX+"*");
    }

    @Override
    public int updateParam(SysParam sysParam) {
        int row = baseMapper.update(sysParam,new QueryWrapper<>(sysParam));
        if (row > 0){
            redisService.set(SysCacheConstant.PARAM_CACHE_KEY_PREFIX+sysParam.getSysParamCode(),sysParam.getSysParamValue());
        }
        return row;
    }

    @Override
    public int insertParam(SysParam sysParam) {
        int row = baseMapper.insert(sysParam);
        if (row > 0){
            redisService.set(SysCacheConstant.PARAM_CACHE_KEY_PREFIX+sysParam.getSysParamCode(),sysParam.getSysParamValue());
        }
        return row;
    }
}




