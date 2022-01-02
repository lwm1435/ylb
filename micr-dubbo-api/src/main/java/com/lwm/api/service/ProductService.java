package com.lwm.api.service;

import com.lwm.common.model.ProductInfo;

import java.util.List;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 18:36
 * @description 产品服务
 */
public interface ProductService {

    /**
     * 根据产品类型分页查询产品
     */
    List<ProductInfo> queryPageByType(Integer type, Integer pageNo, Integer pageSize);

    /**
     * 查询总记录数
     */
    int queryCountByType(Integer type);

    /**
     * 根据产品id查询
     */
    ProductInfo queryDetailById(Integer id);
}
