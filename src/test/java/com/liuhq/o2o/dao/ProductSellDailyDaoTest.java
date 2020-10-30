package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.entity.ProductSellDaily;
import com.liuhq.o2o.entity.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductSellDailyDaoTest {
	
	@Autowired
	private ProductSellDailyDao productSellDailyDao;
	
	@Test
	public void testinsertProductSellDaily() {
		 int effectedNum = productSellDailyDao.insertProductSellDaily();
		 assertEquals(1, effectedNum);
	}
   
	@Test
	public void testqueryProductSellDailyList() {
		ProductSellDaily daily = new ProductSellDaily();
		Shop shop = new Shop();
		shop.setShopId(19L);
		daily.setShop(shop);
		List<ProductSellDaily> productSellDailies = productSellDailyDao.queryProductSellDailyList(daily, null, null);
		System.out.println(productSellDailies);
		assertEquals(1, productSellDailies.size());
		
	}
}
