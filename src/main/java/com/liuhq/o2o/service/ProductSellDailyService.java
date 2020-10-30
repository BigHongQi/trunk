package com.liuhq.o2o.service;

import java.util.Date;
import java.util.List;

import com.liuhq.o2o.entity.ProductSellDaily;

public interface ProductSellDailyService {
    /**
     * 每日定时对所有店铺的商品销量进行统计
     */
	public void dailyCalculate();
	
	/**
	 * 根据查询条件返回商品日销量的统计列表
	 * @param productSellDaily
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDaily,Date beginTime,Date endTime);
}
