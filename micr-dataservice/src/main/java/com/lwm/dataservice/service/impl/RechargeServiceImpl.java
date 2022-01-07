package com.lwm.dataservice.service.impl;

import com.lwm.api.service.RechargeService;
import com.lwm.common.consts.RedisKey;
import com.lwm.common.consts.YLBConst;
import com.lwm.common.model.RechargeRecord;
import com.lwm.common.vo.RechargeRecordVO;
import com.lwm.dataservice.mapper.FinanceAccountMapper;
import com.lwm.dataservice.mapper.RechargeRecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource(name = "rechargeRecordMapper")
    private RechargeRecordMapper rechargeMapper;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    @Resource(name = "financeAccountMapper")
    private FinanceAccountMapper accountMapper;

    @Override
    public List<RechargeRecordVO> queryRechargeRecordByUid(Integer uid, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 6;
        }

        return rechargeMapper.selectPageByUid(uid, (pageNo - 1) * pageSize, pageSize);
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
            int row = rechargeMapper.insert(record);
            if (row == 1){
                //将该充值订单存储到redis数据库中 zset类型, value是该充值订单id,score是创建的时间戳
                redisTemplate.opsForZSet().add(RedisKey.RECHARGE_ORDER_ID_ZSET,orderId,record.getRechargeTime().getTime());
                isOk = true;
            }
        }
        return isOk;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int handlerRecharge(String kq, String orderId, String payResult, String payAmount) {
        //res 结果为0 表示没有处理成功
        int res = 0,row = 0;
        do {
            // 判断是否存在该充值记录
            RechargeRecord record =  rechargeMapper.selectByNo(orderId);
            if (record == null){
                //不存在该充值记录
                res = 4;
                break;
            }
            //判断状态
            if (record.getRechargeStatus() != YLBConst.RECHARGE_PROCESSING){
                //已经处理过的记录
                res = 3;
                break;
            }
            //判断快钱收到的金额和商家订单金额是否一致
            String rechargeMoney = record.getRechargeMoney().multiply(BigDecimal.valueOf(100))
                    .stripTrailingZeros().toPlainString();
            if (!payAmount.equals(rechargeMoney)){
                //金额不一致
                res = 2;
                break;
            }
            // 判断支付是否成功
            if ("10".equals(payResult)){
                //支付成功 更新充值记录状态为1,状态描述为已充值
                record.setRechargeStatus(YLBConst.RECHARGE_SUCCESS);
                record.setRechargeDesc("充值完成");
                row = rechargeMapper.updateByRecharge(record);
                if (row != 1){
                    throw new RuntimeException("更新充值状态异常");
                }
                //更新账户的余额添加上充值的金额
                row = accountMapper.updateBalanceByPay(record.getUid(),record.getRechargeMoney());
                if (row != 1){
                    throw new RuntimeException("更新账户余额异常");
                }
            }else {
                //支付失败 更新充值记录的状态为2 描述为取消充值
                record.setRechargeStatus(YLBConst.RECHARGE_FAILURE);
                record.setRechargeDesc("取消充值");
                row = rechargeMapper.updateByRecharge(record);
                if (row != 1){
                    throw new RuntimeException("更新充值状态异常");
                }
            }
            //充值订单处理完成
            res = 1;
        }while (false);

     return res;
    }
}
