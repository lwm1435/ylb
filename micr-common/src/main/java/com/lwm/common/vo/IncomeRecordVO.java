package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:19
 * @description 用户的收益记录
 */
@Data
public class IncomeRecordVO implements Serializable {
    private Integer id;
    private String productName;
    private String incomeDate;
    private BigDecimal incomeMoney;
    private Integer incomeStatus;
}
