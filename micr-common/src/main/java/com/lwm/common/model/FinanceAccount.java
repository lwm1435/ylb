package com.lwm.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户财务资金账户表
 * @TableName u_finance_account
 */
public class FinanceAccount implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 用户可用资金
     */
    private BigDecimal availableMoney;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户ID
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 用户ID
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 用户可用资金
     */
    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    /**
     * 用户可用资金
     */
    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }
}