package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-01 22:09
 * @description
 */
@Data
public class PlatformInfo implements Serializable {
    /**
     * 历史年化收益率的平均值
     */
    private BigDecimal historyAvgRate;
    /**
     * 平台注册用户总量
     */
    private Long totalUsers;
    /**
     * 累计成交金额
     */
    private BigDecimal totalBidMoney;


}
