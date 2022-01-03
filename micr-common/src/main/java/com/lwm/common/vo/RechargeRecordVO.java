package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:18
 * @description 用户的充值记录
 */
@Data
public class RechargeRecordVO implements Serializable {
    private Integer id;
    private String rechargeDesc;
    private BigDecimal rechargeMoney;
    private String rechargeTime;
    private Integer rechargeStatus;
}
