package com.lwm.pay.controller;

import com.lwm.api.service.RechargeService;
import com.lwm.api.service.UserService;
import com.lwm.common.consts.RedisKey;
import com.lwm.common.model.User;
import com.lwm.pay.service.KuaiQianService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * @author lwm1435@163.com
 * @date 2022-01-04 17:40
 * @description
 */
@Controller
public class KuaiQianController {

    @Resource
    private KuaiQianService kuaiQianService;

    @DubboReference(interfaceClass = UserService.class,version = "1.0")
    private UserService userService;

    @DubboReference(interfaceClass = RechargeService.class,version = "1.0")
    private RechargeService rechargeService;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    /**
     * 接收前端的充值请求
     *
     * @return
     */
    @GetMapping("/receive/vue/recharge")
    public String receiveVueRecharge(@RequestParam Integer userId,
                                     @RequestParam BigDecimal money,
                                     @RequestParam String token,
                                     Model model) {
        //失败页面
        String view = "err";
        //校验入参
        if (userId != null && userId > 0 && money.compareTo(BigDecimal.valueOf(0)) > 0) {
            //查询该用户信息
            User user = userService.QueryUserById(userId);
            if (user != null){
                //生成模板引擎的数据
                Map<String, String> data = kuaiQianService
                        .generateKqPayApiData(money, userId, user.getPhone(),token);
                //创建充值记录---正在充值的状态
                if (rechargeService.addRecharge(userId,money,data.get("orderId"))){
                    //存储数据返回视图
                    model.addAllAttributes(data);
                    view = "kqForm";
                }
            }
        }
        return view;
    }


    /**
     * 接收异步通知 处理充值订单
     */
    @GetMapping("/receive/kq/notify")
    @ResponseBody
    public String receiveKqNotify(HttpServletRequest request) {
        //接收快钱步通知消息，处理订单
        kuaiQianService.doKqNotify(request);
            //订单处理完成(1支付完成2没有支付)删除redis中保留的订单
            redisTemplate.opsForZSet().remove(RedisKey.RECHARGE_ORDER_ID_ZSET,
                    request.getParameter("orderId"));
        return "<result>1</result><redirecturl>http://localhost:7000/user/recharge/showResult</redirecturl>";
    }

    /**
     * 定时任务的接口，向快钱查询充值订单并处理
     */
    @GetMapping("/receive/task/kq/query")
    @ResponseBody
    public void receiveMicrTask(){
       //从redis中获取订单集合
        Set<String> set = redisTemplate.opsForZSet().range(RedisKey.RECHARGE_ORDER_ID_ZSET, 0, -1);
        //向快钱查询充值订单并处理
        kuaiQianService.doKqQueryOrder(set);

        System.out.println("订单处理完成");
    }
}
