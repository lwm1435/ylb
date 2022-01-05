package com.lwm.pay.controller;

import com.lwm.api.service.RechargeService;
import com.lwm.api.service.UserService;
import com.lwm.common.model.User;
import com.lwm.pay.service.KuaiQianService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

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

    @GetMapping("/receive/kq/notify")
    public String receiveKqNotify() {
        return "";
    }
}
