package com.lwm.dataservice.mapper;

import com.lwm.common.model.FinanceAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
* @author Administrator
* @description 针对表【u_finance_account(用户财务资金账户表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.FinanceAccount
*/
public interface FinanceAccountMapper {


    /**
     * 查询用户资金账户 添加行锁，事务结束前其他线程访问不了这一行的数据
     */
    FinanceAccount selectByUidForUpdate(Integer uid);

    /**
     * 更新账户的可用资金减去投资金额
     */
    int updateByUid(@Param("investMoney") BigDecimal investMoney, @Param("uid") Integer uid);

    /**
     * 插入一条账户记录
     */
    int insert(FinanceAccount account);
}
