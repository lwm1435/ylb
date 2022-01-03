package com.lwm.web.controller;

import com.lwm.common.dto.DubboResult;
import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.vo.UserRegister;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 10:12
 * @description
 */
@Api("用户操作接口")
@RestController
public class UserController extends BaseController{

    @ApiOperation(value = "用户注册的短信验证码")
    @GetMapping("/v1/sms/send/register")
    public WebResult sendSmsCode(String phone) throws Exception {
        WebResult webResult = WebResult.fail();
        do{
            //校验参数
            if (StringUtils.isBlank(phone)){
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            //检查手机号是否已经注册
            if (userService.checkPhone(phone)) {
                webResult.setEnumCode(Code.USER_PHONE_EXISTING);
                break;
            }
            //发送验证码
            if (userService.sendSmsCode(phone)){
                webResult.setEnumCode(Code.SUCCESS);
            }
        }while (false);

        return webResult;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/v1/user/register")
    public WebResult userRegister(UserRegister register){
        WebResult webResult = WebResult.fail();
        String phone = register.getPhone();
        String code = register.getCode();
        String password = register.getPassword();
        do{
            //校验参数
            if (StringUtils.isAnyBlank(password,phone,code)){
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            //检查手机号是否已经注册
            if (userService.checkPhone(phone)) {
                webResult.setEnumCode(Code.USER_PHONE_EXISTING);
                break;
            }
            //校验验证码是否正确
            if (!(userService.checkCode(phone,code))){
                webResult.setEnumCode(Code.SMS_CODE_INVALID);
                break;
            }
            //用户注册服务
            DubboResult res = userService.userRegister(phone, password);
            if (!(res.isInvoke())){
                //调用失败
                webResult.setMsg(res.getMsg());
                break;
            }
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(res.getData());
            webResult.setMsg(res.getMsg());
        }while (false);

        return webResult;
    }

}
