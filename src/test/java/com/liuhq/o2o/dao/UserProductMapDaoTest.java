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
import com.liuhq.o2o.entity.UserProductMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserProductMapDaoTest {
	
	@Autowired
	private UserProductMapDao userProductMapDao;
	
	@Test
	public void testinsertUserProductMap() {
		 UserProductMap userProductMap = new UserProductMap();
		 PersonInfo personInfo  = new PersonInfo();
		 personInfo.setUserId(8L);
		 userProductMap.setUserId(8L);
		 userProductMap.setUserName("李翔");
		 userProductMap.setOperator(personInfo);
		 userProductMap.setProductId(15L);
		 userProductMap.setProductName("鲜榨西瓜汁");
		 userProductMap.setShopId(19L);
		 userProductMap.setCreateTime(new Date());
		 userProductMap.setPoint(4);
		 int effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
		 assertEquals(1, effectedNum);
		 
	}
	
	@Test
	public void testqueryUserProductMapList() {
		 UserProductMap userProductCondition = new UserProductMap();
		 userProductCondition.setProductName("鲜");
//		 userProductCondition.setUserName("李");
		 List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapList(userProductCondition, 0, 3);
		 for (UserProductMap userProductMap : userProductMaps) {
			System.out.println(userProductMap);
		}
//		 assertEquals(2, userProductMaps.size());
//		 int count = userProductMapDao.queryUserProductMapCount(userProductCondition);
//		 assertEquals(2, count);
	}

}
