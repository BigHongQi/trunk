package com.liuhq.o2o.service;

import com.liuhq.o2o.dto.ImageHolder;
import com.liuhq.o2o.dto.ShopExecution;
import com.liuhq.o2o.entity.Shop;

public interface ShopService {
     
	 /**
         * 根据shopCondition分页返回相应的店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShopById(long shopId);
	/**
	 *    添加店铺，处理图片
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 */
	 ShopExecution addShop(Shop shop, ImageHolder thumbnail);
	 /**
	      *      修改店铺，处理图片
	  * @param shop
	  * @param shopImgInputStream
	  * @param fileName
	  * @return
	  */
	 ShopExecution modifyShop(Shop shop,  ImageHolder thumbnail);
}
