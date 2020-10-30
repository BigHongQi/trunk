package com.liuhq.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liuhq.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
     * 通过shop id 查询店铺的商品类别
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(Long shopId);
    
    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);
    
    
    /**
     * 
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
    
    
}
