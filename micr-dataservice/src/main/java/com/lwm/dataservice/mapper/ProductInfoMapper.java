package com.lwm.dataservice.mapper;

import com.lwm.common.model.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author Administrator
* @description 针对表【b_product_info(产品信息表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.ProductInfo
*/
public interface ProductInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    /**
     * 查询所有产品的平均利率
     */
    BigDecimal selectAvgRate();

    /**
     * 根据产品类型分页查询产品
     */
    List<ProductInfo> selectPageByType(@Param("type") Integer type, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}
