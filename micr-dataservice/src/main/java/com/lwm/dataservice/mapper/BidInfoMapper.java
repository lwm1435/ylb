package com.lwm.dataservice.mapper;

import com.lwm.common.model.BidInfo;
import com.lwm.common.vo.InvestRank;

import java.math.BigDecimal;
import java.util.List;

/**
* @author Administrator
* @description 针对表【b_bid_info(投资记录表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.BidInfo
*/
public interface BidInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    /**
     * 查询总成交金额
     */
    BigDecimal selectTotalMoney();

    /**
     * 查询投资排行前三
     */
    List<InvestRank> selectInvestRank();
}
