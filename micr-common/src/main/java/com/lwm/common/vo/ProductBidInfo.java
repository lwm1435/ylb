package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 22:01
 * @description 单个产品的投资记录
 */
@Data
public class ProductBidInfo implements Serializable {
    /**
     * 产品id
     */
    private Integer id;
    /**
     * 产品投资日期
     */
    private String bidDateTime;
    /**
     * 产品投资金额
     */
    private BigDecimal bidMoney;
    /**
     * 产品投资人的手机号
     */
    private String  phone;
}
