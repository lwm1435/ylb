package com.lwm.dataservice.service.impl;

import com.lwm.api.service.IncomeService;
import com.lwm.common.vo.IncomeRecordVO;
import com.lwm.dataservice.mapper.IncomeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:25
 * @description
 */
@DubboService(interfaceClass = IncomeService.class,version = "1.0")
public class IncomeServiceImpl implements IncomeService {
    @Resource
    private IncomeRecordMapper incomeRecordMapper;

    @Override
    public List<IncomeRecordVO> queryIncomeRecordByUid(Integer uid, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 6;
        }

        return incomeRecordMapper.selectPageByUid(uid, (pageNo - 1) * pageSize, pageSize);
    }
}
