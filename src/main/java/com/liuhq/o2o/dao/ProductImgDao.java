package com.liuhq.o2o.dao;

import java.util.List;

import com.liuhq.o2o.entity.ProductImg;

public interface ProductImgDao {
	
	/**
     * 批量添加图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);
    
    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);
    
    /**
     * 
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

}
