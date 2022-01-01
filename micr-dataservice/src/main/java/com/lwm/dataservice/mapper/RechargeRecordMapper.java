package com.lwm.dataservice.mapper;

import com.lwm.common.model.RechargeRecord;

/**
* @author Administrator
* @description 针对表【b_recharge_record(充值记录表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.RechargeRecord
*/
public interface RechargeRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

}
