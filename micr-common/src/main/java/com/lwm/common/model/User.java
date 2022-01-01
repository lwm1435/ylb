package com.lwm.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName u_user
 */
public class User implements Serializable {
    /**
     * 用户ID，主键
     */
    private Integer id;

    /**
     * 注册手机号码
     */
    private String phone;

    /**
     * 登录密码，密码长度最大16位
     */
    private String loginPassword;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户身份证号码
     */
    private String idCard;

    /**
     * 注册时间
     */
    private Date addTime;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户头像文件路径
     */
    private String headerImage;

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 用户ID，主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 注册手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 注册手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 登录密码，密码长度最大16位
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * 登录密码，密码长度最大16位
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * 用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 用户姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 用户身份证号码
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 用户身份证号码
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 注册时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 注册时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 最近登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 最近登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 用户头像文件路径
     */
    public String getHeaderImage() {
        return headerImage;
    }

    /**
     * 用户头像文件路径
     */
    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }
}