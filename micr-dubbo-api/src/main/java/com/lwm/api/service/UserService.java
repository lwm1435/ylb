package com.lwm.api.service;

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
}
