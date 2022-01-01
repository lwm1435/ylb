package com.lwm.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录表
 * @TableName b_recharge_record
 */
public class RechargeRecord implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 充值订单号
     */
    private String rechargeNo;

    /**
     * 充值订单状态（0充值中，1充值成功，2充值失败）
     */
    private Integer rechargeStatus;

    /**
     * 充值金额
     */
    private BigDecimal rechargeMoney;

    /**
     * 充值时间
     */
    private Date rechargeTime;

    /**
     * 充值描述
     */
    private String rechargeDesc;

    /**
     * 
     */
    private String channel;

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
     * 用户id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 用户id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 充值订单号
     */
    public String getRechargeNo() {
        return rechargeNo;
    }

    /**
     * 充值订单号
     */
    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    /**
     * 充值订单状态（0充值中，1充值成功，2充值失败）
     */
    public Integer getRechargeStatus() {
        return rechargeStatus;
    }

    /**
     * 充值订单状态（0充值中，1充值成功，2充值失败）
     */
    public void setRechargeStatus(Integer rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    /**
     * 充值金额
     */
    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    /**
     * 充值金额
     */
    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    /**
     * 充值时间
     */
    public Date getRechargeTime() {
        return rechargeTime;
    }

    /**
     * 充值时间
     */
    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    /**
     * 充值描述
     */
    public String getRechargeDesc() {
        return rechargeDesc;
    }

    /**
     * 充值描述
     */
    public void setRechargeDesc(String rechargeDesc) {
        this.rechargeDesc = rechargeDesc;
    }

    /**
     * 
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }
}