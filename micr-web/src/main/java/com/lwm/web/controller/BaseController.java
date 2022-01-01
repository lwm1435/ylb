package com.lwm.web.controller;

import com.lwm.api.service.PlatformInfoService;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 00:01
 * @description
 */
public class BaseController {
    @DubboReference(interfaceClass = PlatformInfoService.class,version = "1.0")
    protected PlatformInfoService platformInfoService;
}
