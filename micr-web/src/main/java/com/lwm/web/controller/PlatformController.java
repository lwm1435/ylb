package com.lwm.web.controller;

import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.vo.PlatformInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 00:01
 * @description
 */
@Api(tags = "盈利宝平台信息")
@RestController
public class PlatformController extends BaseController {

    @ApiOperation(value = "平台三个数据", notes = "平台基本信息注册用户数， 利率平均值， 累计成交金额")
    @GetMapping("/v1/plat/info")
    public WebResult queryIndexInfo(){
        PlatformInfo platformInfo = platformInfoService.queryIndexInfo();
        WebResult webResult = new WebResult();
        webResult.setEnumCode(Code.SUCCESS);
        webResult.setData(platformInfo);
        return webResult;
    }
}
