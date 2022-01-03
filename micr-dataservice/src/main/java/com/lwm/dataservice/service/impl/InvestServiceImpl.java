package com.lwm.dataservice.service.impl;

import com.lwm.api.service.InvestService;
import com.lwm.common.consts.RedisKey;
import com.lwm.common.consts.YLBConst;
import com.lwm.common.dto.DubboResult;
import com.lwm.common.model.BidInfo;
import com.lwm.common.model.FinanceAccount;
import com.lwm.common.model.ProductInfo;
import com.lwm.common.vo.InvestRank;
import com.lwm.common.vo.InvestRecordVO;
import com.lwm.common.vo.ProductBidInfo;
import com.lwm.dataservice.mapper.BidInfoMapper;
import com.lwm.dataservice.mapper.FinanceAccountMapper;
import com.lwm.dataservice.mapper.ProductInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 19:48
 * @description
 */
@DubboService(interfaceClass = InvestService.class, version = "1.0")
public class InvestServiceImpl implements InvestService {
    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    @Resource(name = "financeAccountMapper")
    private FinanceAccountMapper accountMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<InvestRank> queryInvestRank() {
        List<InvestRank> rankList = new ArrayList<>(3);
        InvestRank invest = null;
        //从redis数据库中获取前三个数据
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> set = zSet.reverseRangeWithScores(RedisKey.INVEST_RANKING, 0, 2);
        //如果没有值就去数据库查找并存放在redis中
        if (set != null && set.size() > 0) {
            for (ZSetOperations.TypedTuple<String> item : set) {
                invest = new InvestRank(item.getValue(), BigDecimal.valueOf(item.getScore()));
                rankList.add(invest);
            }
        } else {
            //添加同步代码块避免线程安全问题使用双重检查解决高并发问题
            synchronized (this) {
                set = zSet.reverseRangeWithScores(RedisKey.INVEST_RANKING, 0, 2);
                if (set != null && set.size() > 0) {
                    for (ZSetOperations.TypedTuple<String> item : set) {
                        invest = new InvestRank(item.getValue(), BigDecimal.valueOf(item.getScore()));
                        rankList.add(invest);
                    }
                } else {
                    //去mysql数据库获取数据
                    rankList = bidInfoMapper.selectInvestRank();
                    for (InvestRank item : rankList) {
                        //存放到redis
                        zSet.add(RedisKey.INVEST_RANKING, item.getPhone(), item.getMoney().doubleValue());
                    }
                }
            }
        }
        return rankList;
    }

    @Override
    public List<ProductBidInfo> queryBidInfoByProductId(Integer id, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 6;
        }

        return bidInfoMapper.selectByProdId(id, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public List<InvestRecordVO> queryBidRecordByUid(Integer uid, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 6;
        }

        return bidInfoMapper.selectPageByUid(uid, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized DubboResult InvestProduct(Integer uid, Integer productId, Integer money) {
        DubboResult result = DubboResult.fail();
        do {
            //校验参数
            if (uid == null || productId == null || money == null || money % 100 != 0) {
                result.setMsg("参数有误");
            }
            //查询用户资金账户 添加行锁，事务结束前其他线程访问不了这一行的数据
            FinanceAccount user = accountMapper.selectByUidForUpdate(uid);
            if (user == null) {
                result.setMsg("资金账户不存在");
            }
            BigDecimal investMoney = BigDecimal.valueOf(money);
            //资金账户是否充足 如果投资金额大于可用资金 提示余额不足
            if (investMoney.compareTo(user.getAvailableMoney()) > 0) {
                result.setMsg("可用资金不足");
            }
            //判断产品是否存在
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
            if (productInfo == null) {
                result.setMsg("投资产品不存在");
            }
            //产品是否可售,1.最大限制 2.最小限制 3.产品剩余可投
            BigDecimal leftProductMoney = productInfo.getLeftProductMoney();
            if (investMoney.compareTo(leftProductMoney) > 0
                    || investMoney.compareTo(productInfo.getBidMaxLimit()) > 0
                    || investMoney.compareTo(productInfo.getBidMinLimit()) < 0) {
                result.setMsg("投资金额不满足产品要求");
            }
            //投资金额有效 更新账户的可用资金
            int row = accountMapper.updateByUid(investMoney, uid);
            if (row != 1) {
                throw new RuntimeException("更新账户资金异常");
            }
            //更新产品剩余可投资金额
            leftProductMoney = leftProductMoney.subtract(investMoney);
            productInfo.setLeftProductMoney(leftProductMoney);
            //判断产品是否满标，如果满标，更新产品满标状态1和满标时间
            if (leftProductMoney.compareTo(BigDecimal.valueOf(0)) == 0) {
                productInfo.setProductStatus(YLBConst.PRODUCT_STATUS_SELLED);
                productInfo.setProductFullTime(new Date());
            }
            row = productInfoMapper.updateByPrimaryKeySelective(productInfo);
            if (row != 1) {
                throw new RuntimeException("更新产品信息异常");
            }
            //生成投资记录
            BidInfo bidInfo = new BidInfo();
            bidInfo.setProdId(productId);
            bidInfo.setUid(uid);
            bidInfo.setBidMoney(investMoney);
            bidInfo.setBidTime(new Date());
            bidInfo.setBidStatus(YLBConst.BID_STATUS_SUCC);
            row =  bidInfoMapper.insert(bidInfo);
            if (row != 1){
                throw new RuntimeException("生成投资记录异常");
            }
            //投资成功
            result.setInvoke(true);
            result.setCode(1000);
            result.setMsg("投资成功");
        } while (false);

        return result;
    }
}
