package com.lwm.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资记录表
 * @TableName b_bid_info
 */
public class BidInfo implements Serializable {
    /**
     * 投标记录ID
     */
    private Integer id;

    /**
     * 产品ID
     */
    private Integer prodId;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 投标金额
     */
    private BigDecimal bidMoney;

    /**
     * 投标时间
     */
    private Date bidTime;

    /**
     * 投标状态
     */
    private Integer bidStatus;

    private static final long serialVersionUID = 1L;

    /**
     * 投标记录ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 投标记录ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 产品ID
     */
    public Integer getProdId() {
        return prodId;
    }

    /**
     * 产品ID
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
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
     * 投标金额
     */
    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    /**
     * 投标金额
     */
    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }

    /**
     * 投标时间
     */
    public Date getBidTime() {
        return bidTime;
    }

    /**
     * 投标时间
     */
    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    /**
     * 投标状态
     */
    public Integer getBidStatus() {
        return bidStatus;
    }

    /**
     * 投标状态
     */
    public void setBidStatus(Integer bidStatus) {
        this.bidStatus = bidStatus;
    }
}