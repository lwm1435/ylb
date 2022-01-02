package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 19:28
 * @description 投资排行榜用户的信息
 */
@Data
public class InvestRank implements Serializable {
    private String phone;
    private BigDecimal money;

    public InvestRank(){}

    public InvestRank(String phone, BigDecimal money){
        if (phone != null){
            this.phone =phone.substring(0,3) + "******" + phone.substring(9);
        }
        this.money = money;
    }


}
