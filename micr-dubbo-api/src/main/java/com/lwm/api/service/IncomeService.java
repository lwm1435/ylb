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
}
