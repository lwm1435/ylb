package com.lwm.web.controller;

import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 10:12
 * @description
 */
@RestController
public class UserController extends BaseController{

    @ApiOperation(value = "给用户手机号发送短信验证码")
    @GetMapping("/v1/sms/send/register")
    public WebResult sendSmsCode(String phone) throws Exception {
        WebResult webResult = WebResult.fail();
        //校验参数
        if (StringUtils.isNotBlank(phone)){
            //访问业务层发送短信验证码
            if (userService.sendSmsCode(phone)){
                webResult.setEnumCode(Code.SUCCESS);
            }
        }else {
            webResult.setEnumCode(Code.PARAM_NULL);
        }
        return webResult;
    }
}
