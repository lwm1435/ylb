package com.lwm.dataservice.service.impl;

import com.lwm.api.service.IncomeService;
import com.lwm.common.consts.YLBConst;
import com.lwm.common.model.BidInfo;
import com.lwm.common.model.IncomeRecord;
import com.lwm.common.model.ProductInfo;
import com.lwm.common.vo.IncomeRecordVO;
import com.lwm.dataservice.mapper.BidInfoMapper;
import com.lwm.dataservice.mapper.FinanceAccountMapper;
import com.lwm.dataservice.mapper.IncomeRecordMapper;
import com.lwm.dataservice.mapper.ProductInfoMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 20:25
 * @description
 */
@DubboService(interfaceClass = IncomeService.class, version = "1.0")
public class IncomeServiceImpl implements IncomeService {
    @Resource(name = "incomeRecordMapper")
    private IncomeRecordMapper incomeMapper;
    @Resource(name = "productInfoMapper")
    private ProductInfoMapper prodMapper;
    @Resource(name = "bidInfoMapper")
    private BidInfoMapper bidMapper;
    @Resource(name = "financeAccountMapper")
    private FinanceAccountMapper accountMapper;

    @Override
    public List<IncomeRecordVO> queryIncomeRecordByUid(Integer uid, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 6;
        }

        return incomeMapper.selectPageByUid(uid, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void generateIncomePlan() {
        //定义当天和前一天的时间不包含时分秒
        Date curDay = DateUtils.truncate(new Date(), Calendar.DATE);
        Date preDay = DateUtils.addDays(curDay, -1);
        //获取前一天满标的产品
        List<ProductInfo> productInfos = prodMapper.selectByStatusAndFullTime(curDay, preDay);
        //定义变量
        //遍历前一天的满标产品
        int row;
        Integer pId, pType, cycle;
        Date fullTime, incomeDate;
        BigDecimal dayRate, incomeMoney;
        for (ProductInfo pInfo : productInfos) {
            //产品id
            pId = pInfo.getId();
            //产品类型
            pType = pInfo.getProductType();
            //满标时间
            fullTime = pInfo.getProductFullTime();
            //周期
            cycle = pInfo.getCycle();

            //新手宝产品的收益到期时间 = 满标时间+周期（天）+1
            if (pType == YLBConst.PRODUCT_TYPE_XINSHOUBAO) {
                //新手宝周期是按天算
                incomeDate = DateUtils.addDays(fullTime, cycle + 1);
            } else {
                //其他的周期是按月算要乘以30
                cycle *= 30;
                incomeDate = DateUtils.addDays(fullTime, cycle + 1);
            }
            //日利率 = 年利率数字/100/360
            dayRate = pInfo.getRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);

            //根据产品id获取每笔投资记录
            List<BidInfo> bidInfos = bidMapper.selectByProdId(pId);

            //定义收益记录对象
            IncomeRecord incomeRecord = new IncomeRecord();
            incomeRecord.setProductId(pId);
            incomeRecord.setIncomeDate(incomeDate);
            incomeRecord.setIncomeStatus(YLBConst.INCOME_STATUS_PLAN);//生成收益计划状态--0

            //遍历每笔投资计算利息和到期时间生成收益记录
            for (BidInfo bInfo : bidInfos) {
                //计算当前产品的当前投资利息 = 投资金额 * 日利率 * 周期
                incomeMoney = bInfo.getBidMoney().multiply(dayRate).multiply(BigDecimal.valueOf(cycle));
                //赋值收益记录对象
                incomeRecord.setUid(bInfo.getUid());
                incomeRecord.setBidId(bInfo.getId());
                incomeRecord.setBidMoney(bInfo.getBidMoney());
                incomeRecord.setIncomeMoney(incomeMoney);
                //插入收益记录表 有问题抛异常回滚
                row = incomeMapper.insert(incomeRecord);
                if (row != 1) {
                    throw new RuntimeException("生成收益记录异常");
                }
            }
            //更新产品的状态为2--已生成收益计划
            pInfo.setProductStatus(YLBConst.PRODUCT_STATUS_PLAN);
            row = prodMapper.updateByPrimaryKeySelective(pInfo);
            if (row != 1) {
                throw new RuntimeException("更新产品状态异常");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void incomeBack() {
        //查询到期的未返还的收益记录 1.状态为0未返还  2.前一天的收益计划
        Date preDay = DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DATE), -1);
        List<IncomeRecord> incomeRecords = incomeMapper.selectByIncomeDateAndStatus(preDay);


        int row;
        for (IncomeRecord incomeRecord : incomeRecords) {
            //遍历收益记录,根据uid，bidMoney，incomeMoney更新每个用户账户的余额
            row = accountMapper.updateBalanceByUid(incomeRecord.getUid(),
                    incomeRecord.getBidMoney(), incomeRecord.getIncomeMoney());
            if (row != 1){
                throw new RuntimeException("更行用户账户余额异常");
            }
            //更新该收益的收益状态为1---已返还
            incomeRecord.setIncomeStatus(YLBConst.INCOME_STATUS_BACK);
            row = incomeMapper.updateByIdSelective(incomeRecord);
            if (row != 1){
                throw  new RuntimeException("更新收益记录的状态信息异常");
            }
        }

    }
}
