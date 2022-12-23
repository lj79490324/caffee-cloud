package com.coffee.modules.test.controller;

import com.coffee.common.core.R;
import com.coffee.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 测试类
 * @author rabit
 * @version v1.0
 * @date 2022/8/31 19:18
 */
@RestController
@RequestMapping("test")
public class TestController {


    @Autowired
    private RedisService redisService;

    @RequestMapping("t1")
    public R getData(){
        System.out.println("测试数据");
        Object data = redisService.get("data");
        return R.ok(data);
    }


    @RequestMapping("t2")
    public R getData2(){
        System.out.println("测试数据放入数据");
        boolean data = redisService.set("data", new Random().nextInt());
        return R.error(Boolean.toString(data));
    }
}
