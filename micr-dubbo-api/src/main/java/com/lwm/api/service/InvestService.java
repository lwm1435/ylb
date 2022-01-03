package com.lwm.api.service;

import com.lwm.common.dto.DubboResult;
import com.lwm.common.vo.InvestRank;
import com.lwm.common.vo.InvestRecordVO;
import com.lwm.common.vo.ProductBidInfo;

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

    /**
     * 根据产品id查投资记录，分页查
     */
    List<ProductBidInfo> queryBidInfoByProductId(Integer id, Integer pageNo, Integer PageSize);

    /**
     * 根据用户id分页查询投资记录
     */
    List<InvestRecordVO> queryBidRecordByUid(Integer uid,Integer pageNo,Integer pageSize);

    /**
     * 用户投资产品
     */
    DubboResult InvestProduct(Integer uid, Integer productId, Integer money);
}
