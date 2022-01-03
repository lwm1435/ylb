package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 17:33
 * @description 用户实名的vo对象
 */
@Data
public class RealNameVO implements Serializable {
    private String phone;
    private String name;
    private String code;
    private String idCard;
}
