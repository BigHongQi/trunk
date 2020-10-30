package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.entity.ShopCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTest{

	@Autowired
	ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testGetShopCategoryList() {
		ShopCategory sc = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
		System.out.println(shopCategoryList.size());
		assertEquals(19, shopCategoryList.size());
		sc.setParentId(1L);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(1, shopCategoryList.size());
		sc.setParentId(null);
		sc.setShopCategoryId(0L);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(2, shopCategoryList.size());
	}
}
