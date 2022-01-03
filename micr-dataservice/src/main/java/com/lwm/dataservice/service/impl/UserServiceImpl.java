package com.lwm.dataservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwm.api.service.UserService;
import com.lwm.common.consts.RedisKey;
import com.lwm.common.dto.DubboResult;
import com.lwm.common.model.FinanceAccount;
import com.lwm.common.model.User;
import com.lwm.dataservice.config.JdwxSmsConfig;
import com.lwm.dataservice.mapper.FinanceAccountMapper;
import com.lwm.dataservice.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 10:24
 * @description
 */
@DubboService(interfaceClass = UserService.class, version = "1.0")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource(name = "jdwxSmsConfig")
    private JdwxSmsConfig smsCode;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redis;

    @Resource(name = "financeAccountMapper")
    private FinanceAccountMapper accountMapper;

    @Value("${salt}")
    private String salt;

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

    @Override
    public boolean checkCode(String phone, String code) {
        //任意一个为空返回false
        if (StringUtils.isAnyBlank(phone, code)) {
            return false;
        }
        boolean isCorrect = false;
        //从redis中获取code
        String redisCode = redis.opsForValue().get(RedisKey.SMS_CODE_REGISTER + phone);
        if (code.equals(redisCode)) {
            isCorrect = true;
        }
        return isCorrect;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DubboResult userRegister(String phone, String password) {
        DubboResult result = DubboResult.fail();
        if (StringUtils.isNoneBlank(phone, password)) {
            //查询用户是否已存在
            User user = userMapper.selectByPhone(phone);
            if (user == null) {
                //插入一条用户数据
                user = new User();
                user.setPhone(phone);
                user.setAddTime(new Date());
                //密码加盐后md5加密（前端md5一次，这里第二次）
                user.setLoginPassword(DigestUtils.md5Hex(password + salt));
                int userInsert = 0, accountInsert = 0;
                try {
                    userInsert = userMapper.insertSelective(user);
                    //插入一条用户的账户信息
                    FinanceAccount account = new FinanceAccount();
                    account.setUid(user.getId());
                    account.setAvailableMoney(new BigDecimal("0"));
                    accountInsert = accountMapper.insertSelective(account);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (userInsert == 0 || accountInsert == 0) {
                    result.setMsg("手机号已注册");
                } else {
                    //注册成功
                    result.setInvoke(true);
                    result.setCode(1000);
                    result.setMsg("注册成功");
                }

            } else {
                result.setMsg("手机号已注册");
            }
        }

        return result;
    }

    @Override
    public boolean checkPhone(String phone) {
        boolean isExist = false;
        if (StringUtils.isNotBlank(phone)) {
            User user = userMapper.selectByPhone(phone);
            if (user != null) {
                isExist = true;
            }
        }
        return isExist;
    }


}
