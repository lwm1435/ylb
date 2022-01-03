package com.lwm.dataservice.service.impl;

import com.lwm.api.service.RechargeService;
import com.lwm.common.vo.RechargeRecordVO;
import com.lwm.dataservice.mapper.RechargeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:26
 * @description
 */
@DubboService(interfaceClass = RechargeService.class, version = "1.0")
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public List<RechargeRecordVO> queryRechargeRecordByUid(Integer uid, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 6;
        }

        return rechargeRecordMapper.selectPageByUid(uid, (pageNo - 1) * pageSize, pageSize);
    }
}
