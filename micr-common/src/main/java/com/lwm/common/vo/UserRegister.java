package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 11:52
 * @description
 */
@Data
public class UserRegister implements Serializable {
    private String phone;
    private String password;
    private String code;
}
