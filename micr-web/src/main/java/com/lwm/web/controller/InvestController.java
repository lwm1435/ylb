package com.lwm.web.controller;

import com.lwm.common.consts.RedisKey;
import com.lwm.common.dto.DubboResult;
import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.model.User;
import com.lwm.common.vo.InvestRank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 19:19
 * @description
 */
@Api(tags = "用户投资接口")
@RestController
public class InvestController extends BaseController {

    @ApiOperation(value = "投资排行榜", notes = "投资金额最多的三个用户，显示手机号投资金额")
    @GetMapping("/v1/invest/rank")
    public WebResult queryInvestRank() {
        //默认失败的返回结果
        WebResult webResult = WebResult.fail();
        List<InvestRank> rankList = investService.queryInvestRank();
        if (rankList != null) {
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(rankList);
        }
        return webResult;
    }

    @ApiOperation(value = "投资理财产品")
    @PostMapping("/v1/invest/product")
    public WebResult InvestProduct(@RequestHeader Integer uid,
                                   @RequestParam Integer productId,
                                   @RequestParam Integer money) {
        WebResult webResult = WebResult.fail();
        do {
            //校验参数
            if (uid == null || productId == null || money % 100 != 0) {
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            //验证是否实名
            User user = userService.QueryUserById(uid);
            if (user == null || StringUtils.isBlank(user.getName())){
                webResult.setEnumCode(Code.REQUIRED_REALNAME);
                break;
            }
            //用户投资产品
            DubboResult dubboResult = investService.InvestProduct(uid,productId,money);
            if (!dubboResult.isInvoke()){
                webResult.setMsg(dubboResult.getMsg());
                break;
            }
            //更新redis中的投资排行榜 增加score
            redisTemplate.opsForZSet().incrementScore(RedisKey.INVEST_RANKING, user.getPhone(), money);
            //投资成功
            webResult.setEnumCode(Code.SUCCESS);
        } while (false);

        return webResult;
    }
}
