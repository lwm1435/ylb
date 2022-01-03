package com.lwm.web.controller;

import com.lwm.common.consts.YLBConst;
import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.model.ProductInfo;
import com.lwm.common.vo.PlatformInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 00:01
 * @description
 */
@Api(tags = "盈利宝平台首页信息")
@RestController
public class PlatformController extends BaseController {

    @ApiOperation(value = "平台首页的三个数据", notes = "平台基本信息注册用户数， 利率平均值， 累计成交金额")
    @GetMapping("/v1/plat/info")
    public WebResult queryIndexInfo(){
        WebResult webResult = WebResult.fail();
        PlatformInfo platformInfo = platformService.queryIndexInfo();
        if (platformInfo != null){
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(platformInfo);
        }
        return webResult;
    }

    @ApiOperation(value = "首页的产品信息",notes = "一个新手宝产品， 3个优选类产品， 3个散标类产品")
    @GetMapping("/v1/products/index")
    public WebResult queryIndexProduct(){
        WebResult webResult = WebResult.fail();
        Map<String,Object> map = new HashMap<>(3);
        do{
            //访问业务层
            List<ProductInfo> xinShouBao = productService
                    .queryPageByType(YLBConst.PRODUCT_TYPE_XINSHOUBAO, 1, 3);
            if (xinShouBao == null){
                break;
            }
            List<ProductInfo> sanBiao = productService
                    .queryPageByType(YLBConst.PRODUCT_TYPE_SANBIAO, 1, 3);
            if (sanBiao == null){
                break;
            }
            List<ProductInfo> youXuan = productService
                    .queryPageByType(YLBConst.PRODUCT_TYPE_YOUXUAN, 1, 3);
            if (youXuan == null){
                break;
            }
            map.put("xinShouBao",xinShouBao);
            map.put("sanBiao",sanBiao);
            map.put("youXuan",youXuan);
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(map);
        }while (false);

        return webResult;
    }
}
