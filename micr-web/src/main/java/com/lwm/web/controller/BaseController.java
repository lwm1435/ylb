package com.lwm.web.controller;

import com.lwm.api.service.InvestService;
import com.lwm.api.service.PlatformService;
import com.lwm.api.service.ProductService;
import com.lwm.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 00:01
 * @description
 */
public class BaseController {
    /**
     * 平台首页服务
     */
    @DubboReference(interfaceClass = PlatformService.class,version = "1.0")
    protected PlatformService platformService;

    /**
     * 产品服务
     */
    @DubboReference(interfaceClass = ProductService.class,version = "1.0")
    protected ProductService productService;

    /**
     * 投资服务
     */
    @DubboReference(interfaceClass = InvestService.class,version = "1.0")
    protected InvestService investService;

    @DubboReference(interfaceClass = UserService.class,version = "1.0")
    protected UserService userService;

    /**
     * redis服务
     */
    @Resource(name = "stringRedisTemplate")
    protected StringRedisTemplate redisTemplate;
}
