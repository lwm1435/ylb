package com.lwm.api.service;

import com.lwm.common.vo.IncomeRecordVO;

import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:25
 * @description
 */
public interface IncomeService {

    /**
     *  根据用户id分页查询收益记录
     */
    List<IncomeRecordVO> queryIncomeRecordByUid(Integer uid, Integer pageNo, Integer pageSize);

    /**
     * 生成收益计划定时任务服务
     */
    void generateIncomePlan();

    /**
     * 收益返还定时任务服务
     */
    void incomeBack();
}
