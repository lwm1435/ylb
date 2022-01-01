package com.lwm.dataservice.mapper;

import com.lwm.common.model.FinanceAccount;

/**
* @author Administrator
* @description 针对表【u_finance_account(用户财务资金账户表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.FinanceAccount
*/
public interface FinanceAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);

}
