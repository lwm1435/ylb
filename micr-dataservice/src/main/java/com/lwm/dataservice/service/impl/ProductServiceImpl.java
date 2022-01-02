package com.lwm.dataservice.service.impl;

import com.lwm.api.service.ProductService;
import com.lwm.common.model.ProductInfo;
import com.lwm.dataservice.mapper.ProductInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 18:39
 * @description
 */
@DubboService(interfaceClass = ProductService.class,version = "1.0")
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> queryPageByType(Integer type, Integer pageNo, Integer pageSize) {
        List<ProductInfo> productInfos = null;

        //校验参数
        if (type < 0) {
            return productInfos;
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 6;
        }
        //访问数据库
        productInfos = productInfoMapper.selectPageByType(type, (pageNo - 1) * pageSize, pageSize);

        return productInfos;
    }

    @Override
    public int queryCountByType(Integer type) {
        int row = 0;
        //校验参数
        if (type < 0) {
            return row;
        }
        //访问数据库
        row = productInfoMapper.selectCountByType(type);

        return row;
    }
}
