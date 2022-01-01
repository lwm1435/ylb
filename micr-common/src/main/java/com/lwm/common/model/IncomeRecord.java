package com.lwm.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 收益记录表
 * @TableName b_income_record
 */
public class IncomeRecord implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 投标记录ID
     */
    private Integer bidId;

    /**
     * 投资金额
     */
    private BigDecimal bidMoney;

    /**
     * 产品到期时间
     */
    private Date incomeDate;

    /**
     * 利息金额
     */
    private BigDecimal incomeMoney;

    /**
     * 收益状态（0未返，1已返）
     */
    private Integer incomeStatus;

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
     * 产品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 产品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 投标记录ID
     */
    public Integer getBidId() {
        return bidId;
    }

    /**
     * 投标记录ID
     */
    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    /**
     * 投资金额
     */
    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    /**
     * 投资金额
     */
    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }

    /**
     * 产品到期时间
     */
    public Date getIncomeDate() {
        return incomeDate;
    }

    /**
     * 产品到期时间
     */
    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    /**
     * 利息金额
     */
    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    /**
     * 利息金额
     */
    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    /**
     * 收益状态（0未返，1已返）
     */
    public Integer getIncomeStatus() {
        return incomeStatus;
    }

    /**
     * 收益状态（0未返，1已返）
     */
    public void setIncomeStatus(Integer incomeStatus) {
        this.incomeStatus = incomeStatus;
    }
}