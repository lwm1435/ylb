package com.lwm.api.service;

import com.lwm.common.dto.DubboResult;
import com.lwm.common.model.User;
import com.lwm.common.vo.UserAccountBO;

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
    boolean checkByPhone(String phone);

    /**
     * 登录信息验证
     */
    User checkLogin(String phone, String password);

    /**
     * 根据id查询用户信息
     */
    User QueryUserById(Integer uid);

    /**
     * 用户实名服务
     */
    boolean realName(Integer uid, String name, String idCard) throws Exception;

    /**
     * 根据用户id查询用户和账户信息
     */
    UserAccountBO queryUserAccountInfo(Integer uid);
}
