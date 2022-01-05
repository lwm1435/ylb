package com.lwm.dataservice.service.impl;

import com.lwm.api.service.RechargeService;
import com.lwm.common.consts.RedisKey;
import com.lwm.common.consts.YLBConst;
import com.lwm.common.model.RechargeRecord;
import com.lwm.common.vo.RechargeRecordVO;
import com.lwm.dataservice.mapper.RechargeRecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

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

    @Override
    public boolean addRecharge(Integer userId, BigDecimal money, String orderId) {
        boolean isOk = false;
        //校验入参
        if (userId != null && userId > 0 && money.compareTo(BigDecimal.valueOf(0)) > 0
                && StringUtils.isNotBlank(orderId)){
            //创建充值记录对象 赋值
            RechargeRecord record = new RechargeRecord();
            record.setUid(userId);
            record.setRechargeNo(orderId);
            record.setRechargeStatus(YLBConst.RECHARGE_PROCESSING);
            record.setRechargeMoney(money);
            record.setRechargeTime(new Date());
            record.setChannel("kq");
            record.setRechargeDesc("快钱充值服务进行中");
            //插入一条充值记录
            int row = rechargeRecordMapper.insert(record);
            if (row == 1){
                //将该充值订单存储到redis数据库中 zset类型, value是该充值订单id,score是创建的时间戳
                redisTemplate.opsForZSet().add(RedisKey.RECHARGE_ORDER_ID_ZSET,orderId,record.getRechargeTime().getTime());
                isOk = true;
            }
        }
        return isOk;
    }
}
