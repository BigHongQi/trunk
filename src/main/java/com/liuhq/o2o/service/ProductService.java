package com.liuhq.o2o.service;

import java.util.List;

import com.liuhq.o2o.dto.ImageHolder;
import com.liuhq.o2o.dto.ProductExecution;
import com.liuhq.o2o.entity.Product;
import com.liuhq.o2o.exceptiopns.ProductOperationException;

public interface ProductService {
	   /**
     *  添加商品以及图片处理
     * @param product
     * @param thumbnail
     * @param thumbnailName
     * @param productImgList
     * @param productImgNameList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product,ImageHolder thumbnail,
                                 List<ImageHolder> imageHolderList) throws ProductOperationException;
    
    /**
     * 
     * @param productId
     * @return
     */
    Product getProductById(long productId);
    
    /**
     * 
     * @param product
     * @param thumbnail
     * @param imageHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,
            List<ImageHolder> imageHolderList) throws ProductOperationException;
    
    /**
     * 
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize)throws ProductOperationException;
}



