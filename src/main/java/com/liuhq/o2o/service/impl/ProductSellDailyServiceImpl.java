package com.liuhq.o2o.service.impl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuhq.o2o.dao.ProductSellDailyDao;
import com.liuhq.o2o.entity.ProductSellDaily;
import com.liuhq.o2o.service.ProductSellDailyService;
@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {
     
	private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);
	@Autowired
	ProductSellDailyDao productSellDailyDao;
	@Override
	public void dailyCalculate() {
		// TODO Auto-generated method stub
		log.info("Quartz Running");
		productSellDailyDao.insertProductSellDaily();
       
	}
	@Override
	public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDaily, Date beginTime,
			Date endTime) {
		// TODO Auto-generated method stub
		return productSellDailyDao.queryProductSellDailyList(productSellDaily, beginTime, endTime);
	}

}
