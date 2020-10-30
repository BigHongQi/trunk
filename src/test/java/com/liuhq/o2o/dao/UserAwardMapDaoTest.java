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

import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.entity.UserAwardMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAwardMapDaoTest {
   
	@Autowired
	private UserAwardMapDao userAwardMapDao;
	
	//@Test
	public void testinsertUserAwardMap() {
		UserAwardMap userAwardMap =new UserAwardMap();
		PersonInfo customer = new PersonInfo();
		customer.setUserId(8L);
		userAwardMap.setUserId(8L);
		userAwardMap.setOperator(customer);
		userAwardMap.setAwardId(3L);
		userAwardMap.setShopId(19L);
		userAwardMap.setCreateTime(new Date());
		userAwardMap.setUsedStatus(1);
		userAwardMap.setPoint(4);
		int effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
		
	}
	@Test
	public void testqueryUserAwardMapList() {
		UserAwardMap userAwardMap =new UserAwardMap();
		List<UserAwardMap> userAwardMapsList = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 3);
		assertEquals(2, userAwardMapsList.size());
		int count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
		assertEquals(2, count);
		userAwardMap.setUserName("李");
		userAwardMapsList = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 3);
		assertEquals(2, userAwardMapsList.size());
		count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
		assertEquals(2, count);
		userAwardMap = userAwardMapDao.queryUserAwardMapById(1L);
		System.out.println(userAwardMap);
		assertEquals("我的奖品", userAwardMap.getAwardName());
	}
	@Test
	public void testupdateUserAwardMap() {
		UserAwardMap userAwardMap =new UserAwardMap();
		userAwardMap = userAwardMapDao.queryUserAwardMapById(1L);
		userAwardMap.setUsedStatus(1);
		int effectedNum = userAwardMapDao.updateUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
		
	}
	
}
