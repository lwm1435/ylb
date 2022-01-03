package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 19:39
 * @description
 */
@Data
public class UserAccountBO implements Serializable {
    private Integer uid;
    private String name;
    private String phone;
    private String headerImage;
    private Date lastLoginTime;
    private BigDecimal availableMoney;
}
