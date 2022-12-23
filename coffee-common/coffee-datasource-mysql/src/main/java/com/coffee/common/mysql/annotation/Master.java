package com.coffee.common.mysql.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;


/**
 *
 * 主数据源配置
 * @author rabit
 * @version v1.0
 * @date 2022/9/1 13:20
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("master")
public @interface Master {

}
