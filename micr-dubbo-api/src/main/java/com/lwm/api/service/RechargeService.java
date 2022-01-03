package com.lwm.api.service;

import com.lwm.common.vo.RechargeRecordVO;

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

}
