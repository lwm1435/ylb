package com.lwm.web.controller;

import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.model.ProductInfo;
import com.lwm.common.vo.Page;
import com.lwm.common.vo.ProductBidInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 20:59
 * @description
 */
@Api("产品信息接口")
@RestController
public class ProductController extends BaseController{

    @ApiOperation("根据产品类型分页查数据")
    @GetMapping("/v1/products/list")
    public WebResult queryProductList(@RequestParam("type")Integer type,
                                      @RequestParam("pageNo")Integer pageNo){
        WebResult webResult = WebResult.fail();
        do {
            //校验入参
            if (type == null || type < 0) {
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            if (pageNo == null || pageNo < 1) {
                pageNo = 1;
            }
            //查询当前页数据
            List<ProductInfo> productInfos = productService.queryPageByType(type, pageNo, Page.PAGE_SIZE);
            if (productInfos == null){
                break;
            }
            //查询总记录数
            int totalRecords = productService.queryCountByType(type);
            //封装数据
            Page page = new Page(pageNo,Page.PAGE_SIZE,totalRecords);
            Map<String,Object> map = new HashMap<>(2);
            map.put("page",page);
            map.put("productInfos",productInfos);
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(map);
        }while (false);

        return webResult;
    }

    @ApiOperation("产品详情页查询")
    @GetMapping("/product/info/detail")
    public WebResult queryProductDetail(@RequestParam("id")Integer id){
        WebResult webResult = WebResult.fail();
        //根据id查单个产品
        ProductInfo productInfo = productService.queryDetailById(id);
        if (productInfo != null){
            //根据产品id查多个投资记录，分页查
            List<ProductBidInfo> bidInfos = investService.queryBidInfoByProductId(id,1,5);
            if (bidInfos != null){
                //封装数据
                Map<String,Object> map = new HashMap<>(2);
                map.put("bidInfos",bidInfos);
                map.put("productInfo",productInfo);
                webResult.setEnumCode(Code.SUCCESS);
                webResult.setData(map);
            }
        }else {
            webResult.setEnumCode(Code.PRODUCT_NOT_EXISTS);
        }
        return webResult;
    }
}
