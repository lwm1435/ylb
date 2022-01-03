package com.lwm.dataservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwm.api.service.UserService;
import com.lwm.common.consts.RedisKey;
import com.lwm.dataservice.config.JdwxSmsConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 10:24
 * @description
 */
@DubboService(interfaceClass = UserService.class, version = "1.0")
public class UserServiceImpl implements UserService {

    @Resource(name = "jdwxSmsConfig")
    private JdwxSmsConfig smsCode;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redis;

    @Override
    public boolean sendSmsCode(String phone) throws Exception {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        boolean isSend = false;
        //生成4位随机验证码
        String code = RandomStringUtils.randomNumeric(4);
        //调用第三方接口发送短信
//        Map<String, String> params = new HashMap<>(3);
//        params.put("mobile", phone);
//        params.put("appkey", smsCode.getAppkey());
//        params.put("content", smsCode.getContent());
//        String json = HttpClientUtils.doGet(smsCode.getUrl(), params);
        //使用假数据
        String json = "{\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 1305,\n" +
                "    \"msg\": \"查询成功\",\n" +
                "    \"result\": {\n" +
                "        \"ReturnStatus\": \"Success\",\n" +
                "        \"Message\": \"ok\",\n" +
                "        \"RemainPoint\": 420842,\n" +
                "        \"TaskID\": 18424321,\n" +
                "        \"SuccessCounts\": 1\n" +
                "    }\n" +
                "}";
        //校验参数 解析参数
        if (StringUtils.isNotBlank(json)) {
            //转为对象
            JSONObject obj = JSONObject.parseObject(json);
            if ("10000".equals(obj.getString("code")) &&
                    "Success".equals(obj.getJSONObject("result").getString("ReturnStatus"))) {
                //存储到redis三分钟 存储的key为常量+手机号 value为验证码
                redis.opsForValue().set(RedisKey.SMS_CODE_REGISTER + phone, code, 3, TimeUnit.MINUTES);
                isSend = true;
            }
        }
        return isSend;
    }


}
