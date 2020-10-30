package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.liuhq.o2o.entity.Area;
import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.entity.ShopCategory;
import com.liuhq.o2o.utils.weixin.SignUtil;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopDaoTest{
   
	@Autowired
	private ShopDao shopDao;
	
	 @Test
	    public void testQueryShopList() {
	        Shop shopCondition = new Shop();
	        ShopCategory shopCategory = new ShopCategory();
	        shopCategory.setParentId(12L);
//	        shopCategory.setShopCategoryId(20L);
	        shopCondition.setShopCategory(shopCategory);
//	        shopCondition.setOwnerId(8L);
//	        Area area=new Area();
//	        area.setAreaId(3L);
//	        shopCondition.setArea(area);
	        List<Shop> list =shopDao.queryShopList(shopCondition, 0, 10);
	        System.out.println(list.size());
	    }

	    @Test
	    public void testQueryShopCount() {
	        Shop shopCondition = new Shop();
//	        shopCondition.setOwnerId(8L);
//	        Area area=new Area();
//	        area.setAreaId(3L);
//	        shopCondition.setArea(area);
	        ShopCategory shopCategory = new ShopCategory();
	        shopCategory.setParentId(12L);
	        shopCondition.setShopCategory(shopCategory);
	        int count =shopDao.queryShopCount(shopCondition);
	        System.out.println(count);
	    }
	
	@Test
	public void testInsertShop() {
		Shop shop = new Shop();
		shop.setOwnerId(8L);
		Area area = new Area();
		area.setAreaId(3L);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(10L);
		shop.setShopName("mytest1");
		shop.setShopDesc("mytest1");
		shop.setShopAddr("testaddr1");
		shop.setPhone("13810524526");
		shop.setShopImg("test1");
		shop.setLongitude(1D);
		shop.setLatitude(1D);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(32L);
		shop.setShopName("香飘飘");
		shop.setShopDesc("香飘飘");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
	@Test
	public void TestQueryByShopId() {
		long shopId=41L;
		Shop shop=shopDao.queryByShopId(shopId);
		System.out.println(shop.getArea().getAreaName());
	}
	
	@Test
	public void testget() {
		boolean result=SignUtil.checkSignature("6B5593DEE582B30165AC76085CAE1A83347D15E7", "45644", "qwwer");
		System.out.println(result);
	}
}
