package com.lwm.dataservice.service.impl;

import com.lwm.api.service.InvestService;
import com.lwm.common.consts.RedisKey;
import com.lwm.common.vo.InvestRank;
import com.lwm.dataservice.mapper.BidInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 19:48
 * @description
 */
@DubboService(interfaceClass = InvestService.class,version = "1.0")
public class InvestServiceImpl implements InvestService {
    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;
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
}
