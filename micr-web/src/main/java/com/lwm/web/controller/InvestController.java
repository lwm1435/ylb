package com.lwm.web.controller;

import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.vo.InvestRank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (rankList != null){
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(rankList);
        }
        return webResult;
    }
}
