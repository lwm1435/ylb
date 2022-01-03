package com.lwm.common.enums;

/**
 * @author lwm1435@163.com
 * @date 2021-12-14 20:52
 */
public enum Code {
    /**
     * 请求成功的信息，状态码为1000
     */
    SUCCESS(1000,"请求成功"),
    /**
     * 请求失败的信息，状态码为2000
     */
    FAIlURE(2000,"请稍后重试"),
    /**
     * 参数为空的信息，状态码为1001
     */
    PARAM_NULL(1001,"参数有误"),
    /**
     * 查询结果为空
     */
    PRODUCT_NOT_EXISTS(1002,"查询结果为空"),
    /**
     * 验证码无效
     */
    SMS_CODE_INVALID(1003,"验证码无效"),
    /**
     * 验证码无效
     */
    USER_PHONE_EXISTING(1004,"手机号已注册"),
    /**
     * 登录信息有误
     */
    USER_LOGIN_INVALID(1005,"登录信息有误"),

    /**
     * 权限认证无效
     */
    TOKEN_INVALID(1006,"权限认证无效"),

    /**
     * 请先进行实名认证
     */
    REQUIRED_REALNAME(1007,"请先进行实名认证"),

    /**
     * 无需重复实名认证
     */
    NOT_RETRY_REALNAME(1008,"无需重复实名认证");

    /**
     * 状态码
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;

    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
