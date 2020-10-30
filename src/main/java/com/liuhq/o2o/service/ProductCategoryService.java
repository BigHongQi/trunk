package com.liuhq.o2o.service;

import java.util.List;

import com.liuhq.o2o.dto.ProductCategoryExecution;
import com.liuhq.o2o.entity.ProductCategory;
import com.liuhq.o2o.exceptiopns.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
     * 查询指定某个店铺下的所有的商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategroyList(Long shopId);
    
    /**
     * 批量添加CategoryList
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;
    
    /**
     * 将此类别下的商品里的类别id置为空，在删除该商品类别
     * @param productCategoryId
     * @param shopID
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) 
        throws ProductCategoryOperationException;
}
