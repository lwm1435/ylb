package com.lwm.dataservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwm.api.service.PlatformService;
import com.lwm.common.consts.RedisKey;
import com.lwm.common.vo.PlatformInfo;
import com.lwm.dataservice.mapper.BidInfoMapper;
import com.lwm.dataservice.mapper.ProductInfoMapper;
import com.lwm.dataservice.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author lwm1435@163.com
 * @date 2022-01-01 22:25
 * @description
 */
@DubboService(interfaceClass = PlatformService.class,version = "1.0")
public class PlatformServiceImpl implements PlatformService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;


    @Override
    public PlatformInfo queryIndexInfo() {
        PlatformInfo platformInfo = null;
        //从redis中获取数据
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String json = ops.get(RedisKey.PLATFORM_INDEX_DATA);
        if (json != null) {
            //转成对象返回
            platformInfo = JSONObject.parseObject(json,PlatformInfo.class);
        }else {
            //没有值的情况，使用同步代码块解决多线程不安全问题
            synchronized (this){
                json = ops.get(RedisKey.PLATFORM_INDEX_DATA);
                //双重检查，提高高并发的能力
                if (json != null){
                    //转成对象返回
                    platformInfo = JSONObject.parseObject(json,PlatformInfo.class);
                }else {
                    //访问数据库获取数据 封装数据
                    BigDecimal totalMoney =  bidInfoMapper.selectTotalMoney();
                    Long totalUser = userMapper.selectTotalUser();
                    BigDecimal avgRate  = productInfoMapper.selectAvgRate();
                    platformInfo = new PlatformInfo();
                    platformInfo.setHistoryAvgRate(avgRate);
                    platformInfo.setTotalUsers(totalUser);
                    platformInfo.setTotalBidMoney(totalMoney);
                }
            }
        }

        return platformInfo;
    }
}
