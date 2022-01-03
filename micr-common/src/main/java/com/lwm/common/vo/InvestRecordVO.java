package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:16
 * @description 用户的投资记录
 */
@Data
public class InvestRecordVO implements Serializable {
    private Integer id;
    private String productName;
    private BigDecimal bidMoney;
    private String bidTime;
}
