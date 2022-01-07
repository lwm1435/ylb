package com.lwm.api.service;

import com.lwm.common.vo.RechargeRecordVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:26
 * @description
 */
public interface RechargeService {

    /**
     * 根据用户id分页查询充值记录
     */
    List<RechargeRecordVO> queryRechargeRecordByUid(Integer uid, Integer pageNo, Integer pageSize);

    /**
     * 添加一条充值记录
     * @param userId 用户id
     * @param money 充值金额
     * @param orderId 订单id
     */
    boolean addRecharge(Integer userId, BigDecimal money, String orderId);

    /**
     * 处理充值订单
     * @param kq 充值渠道时kq
     * @param orderId 充值订单id
     * @param payResult 用户向快钱支付的结果
     * @param payAmount 用户向快钱支付的金额
     */
    int handlerRecharge(String kq, String orderId, String payResult, String payAmount);
}
