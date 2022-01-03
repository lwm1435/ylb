package com.lwm.api.service;

import com.lwm.common.dto.DubboResult;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 10:23
 * @description
 */
public interface UserService {

    /**
     * 给用户手机号发送短信验证码
     * @throws Exception get请求io异常
     */
    boolean sendSmsCode(String phone) throws Exception;

    /**
     * 校验验证码是否正确
     */
    boolean checkCode(String phone, String code);

    /**
     * 用户注册服务
     */
    DubboResult userRegister(String phone, String password);

    /**
     * 检查手机号是否已注册
     */
    boolean checkPhone(String phone);
}
