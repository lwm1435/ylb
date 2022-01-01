package com.lwm.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品信息表
 * @TableName b_product_info
 */
public class ProductInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品利率
     */
    private BigDecimal rate;

    /**
     * 产品期限
     */
    private Integer cycle;

    /**
     * 产品发布时间
     */
    private Date releaseTime;

    /**
     * 产品类型 0新手宝，1优选产品，2散标产品
     */
    private Integer productType;

    /**
     * 产品编号
     */
    private String productNo;

    /**
     * 产品金额
     */
    private BigDecimal productMoney;

    /**
     * 产品剩余可投金额
     */
    private BigDecimal leftProductMoney;

    /**
     * 最低投资金额，即起投金额
     */
    private BigDecimal bidMinLimit;

    /**
     * 最高投资金额，即最多能投多少金额
     */
    private BigDecimal bidMaxLimit;

    /**
     * 产品状态（0未满标，1已满标，2满标已生成收益计划）
     */
    private Integer productStatus;

    /**
     * 产品投资满标时间
     */
    private Date productFullTime;

    /**
     * 产品描述
     */
    private String productDesc;

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
     * 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 产品利率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 产品利率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 产品期限
     */
    public Integer getCycle() {
        return cycle;
    }

    /**
     * 产品期限
     */
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    /**
     * 产品发布时间
     */
    public Date getReleaseTime() {
        return releaseTime;
    }

    /**
     * 产品发布时间
     */
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * 产品类型 0新手宝，1优选产品，2散标产品
     */
    public Integer getProductType() {
        return productType;
    }

    /**
     * 产品类型 0新手宝，1优选产品，2散标产品
     */
    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    /**
     * 产品编号
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * 产品编号
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /**
     * 产品金额
     */
    public BigDecimal getProductMoney() {
        return productMoney;
    }

    /**
     * 产品金额
     */
    public void setProductMoney(BigDecimal productMoney) {
        this.productMoney = productMoney;
    }

    /**
     * 产品剩余可投金额
     */
    public BigDecimal getLeftProductMoney() {
        return leftProductMoney;
    }

    /**
     * 产品剩余可投金额
     */
    public void setLeftProductMoney(BigDecimal leftProductMoney) {
        this.leftProductMoney = leftProductMoney;
    }

    /**
     * 最低投资金额，即起投金额
     */
    public BigDecimal getBidMinLimit() {
        return bidMinLimit;
    }

    /**
     * 最低投资金额，即起投金额
     */
    public void setBidMinLimit(BigDecimal bidMinLimit) {
        this.bidMinLimit = bidMinLimit;
    }

    /**
     * 最高投资金额，即最多能投多少金额
     */
    public BigDecimal getBidMaxLimit() {
        return bidMaxLimit;
    }

    /**
     * 最高投资金额，即最多能投多少金额
     */
    public void setBidMaxLimit(BigDecimal bidMaxLimit) {
        this.bidMaxLimit = bidMaxLimit;
    }

    /**
     * 产品状态（0未满标，1已满标，2满标已生成收益计划）
     */
    public Integer getProductStatus() {
        return productStatus;
    }

    /**
     * 产品状态（0未满标，1已满标，2满标已生成收益计划）
     */
    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    /**
     * 产品投资满标时间
     */
    public Date getProductFullTime() {
        return productFullTime;
    }

    /**
     * 产品投资满标时间
     */
    public void setProductFullTime(Date productFullTime) {
        this.productFullTime = productFullTime;
    }

    /**
     * 产品描述
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * 产品描述
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}