package com.lwm.dataservice.mapper;

import com.lwm.common.vo.IncomeRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【b_income_record(收益记录表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.IncomeRecord
*/
public interface IncomeRecordMapper {

    /**
     * 根据用户id分页查询收益记录
     */
    List<IncomeRecordVO> selectPageByUid(@Param("uid") Integer uid,
                                         @Param("offset") Integer offset,
                                         @Param("pageSize") Integer pageSize);
}
