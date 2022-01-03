package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 19:32
 * @description 用户和账户基本信息的vo对象
 */
@Data
public class UserAccountVO implements Serializable {
    private Integer uid;
    private String name;
    private String phone;
    private String lastLoginTime;
    private BigDecimal availableMoney;
}
