package com.lwm.web.controller;

import com.lwm.api.service.*;
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

    /**
     * 用户服务
     */
    @DubboReference(interfaceClass = UserService.class,version = "1.0")
    protected UserService userService;

    /**
     * 充值服务
     */
    @DubboReference(interfaceClass = RechargeService.class,version = "1.0")
    protected RechargeService rechargeService;

    /**
     * 收益服务
     */
    @DubboReference(interfaceClass = IncomeService.class,version = "1.0")
    protected IncomeService incomeService;

    /**
     * redis服务
     */
    @Resource(name = "stringRedisTemplate")
    protected StringRedisTemplate redisTemplate;
}
