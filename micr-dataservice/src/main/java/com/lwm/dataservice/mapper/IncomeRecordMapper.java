package com.lwm.dataservice.mapper;

import com.lwm.common.model.IncomeRecord;

/**
* @author Administrator
* @description 针对表【b_income_record(收益记录表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.IncomeRecord
*/
public interface IncomeRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);

}
