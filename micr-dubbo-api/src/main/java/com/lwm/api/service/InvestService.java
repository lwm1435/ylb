package com.lwm.api.service;

import com.lwm.common.vo.InvestRank;

import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 19:48
 * @description 投资服务
 */
public interface InvestService {

    /**
     * 查询投资排行榜前三
     */
    List<InvestRank> queryInvestRank();

}
