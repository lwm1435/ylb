package com.lwm.dataservice.mapper;

import com.lwm.common.model.RechargeRecord;
import com.lwm.common.vo.RechargeRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【b_recharge_record(充值记录表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.RechargeRecord
*/
public interface RechargeRecordMapper {

    /**
     * 根据id查单条
     */
    RechargeRecord selectByPrimaryKey(Long id);

    /**
     * 根据用户id分页查询充值记录
     */
    List<RechargeRecordVO> selectPageByUid(@Param("uid") Integer uid,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize);

    /**
     * 插入一条充值记录
     */
    int insert(RechargeRecord rechargeRecord);

}
