package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.entity.ShopAuthMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopAuthMapDaoTest{
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Test
	public void testAInsertShopAuthMap() throws Exception {
		ShopAuthMap shopAuthMap = new ShopAuthMap();
		shopAuthMap.setEmployeeId(8L);
		shopAuthMap.setShopId(19L);
		shopAuthMap.setName("test1");
		shopAuthMap.setTitle("CEO");
		shopAuthMap.setTitleFlag(1);
		shopAuthMap.setCreateTime(new Date());
		shopAuthMap.setLastEditTime(new Date());
		shopAuthMap.setEnableStatus(1);
		int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryShopAuthMapListByShopId() throws Exception {
       List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(19, 0, 2);
       assertEquals(1, shopAuthMapList.size());
       int count = shopAuthMapDao.queryShopAuthCountByShopId(19);
       assertEquals(1, count);
       ShopAuthMap shopAuthMap = new ShopAuthMap();
       shopAuthMap = shopAuthMapDao.queryShopAuthMapById(shopAuthMapList.get(0).getShopAuthId());
       System.out.println(shopAuthMap);
	}

	@Test
	public void testCUpdateShopAuthMap() throws Exception {
		ShopAuthMap shopAuthMap = new ShopAuthMap();
		shopAuthMap.setShopAuthId(1L);
		shopAuthMap.setTitle("CCO");
		shopAuthMap.setTitleFlag(2);
		int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testDeleteShopAuthMap() throws Exception {
		int effectedNum = shopAuthMapDao.deleteShopAuthMap(29L);
		assertEquals(1, effectedNum);
	}
}
