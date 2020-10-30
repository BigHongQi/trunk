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

import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.entity.UserShopMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserShopMapDaoTest {
	
	@Autowired
    private	UserShopMapDao userShopMapDao;
	
	@Test
	public void testinsertUserShopMap() {
		UserShopMap userShopMap = new UserShopMap();
		userShopMap.setUserId(8L);
		userShopMap.setShopId(19L);
		userShopMap.setCreateTime(new Date());
		userShopMap.setPoint(1);
		int effectedNum = userShopMapDao.insertUserShopMap(userShopMap);
		assertEquals(1, effectedNum);
		
		UserShopMap userShopMap2 = new UserShopMap();
		userShopMap2.setUserId(1L);
		userShopMap2.setShopId(32L);
		userShopMap2.setCreateTime(new Date());
		userShopMap2.setPoint(1);
		int effectedNum2 = userShopMapDao.insertUserShopMap(userShopMap2);
		assertEquals(1, effectedNum2);
	}
	@Test
	public void testqueryUserShopMapList() {
		UserShopMap userShopMap = new UserShopMap();
//		List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMap, 0, 3);
//		assertEquals(2, userShopMapList.size());
//		int count = userShopMapDao.queryUserShopMapCount(userShopMap);
//		assertEquals(2, count);
		Shop shop = new Shop();
		shop.setShopId(19L);
		userShopMap.setShopId(19L);
		List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMap, 0, 3);
		assertEquals(1, userShopMapList.size());
		int count = userShopMapDao.queryUserShopMapCount(userShopMap);
		assertEquals(1, count);
//		userShopMap = userShopMapDao.queryUserShopMap(8, 19);
//		System.out.println(userShopMap);
	}
	@Test
	public void testupdateUserShopMapPoint() {
		UserShopMap userShopMap = new UserShopMap();
		userShopMap = userShopMapDao.queryUserShopMap(8, 19);
		userShopMap.setPoint(3);
		System.out.println(userShopMap);
		int effectedNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
		assertEquals(1, effectedNum);
		
	}

}
