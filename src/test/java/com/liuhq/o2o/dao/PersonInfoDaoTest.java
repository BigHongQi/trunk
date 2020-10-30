package com.liuhq.o2o.dao;



import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.liuhq.o2o.entity.PersonInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoDaoTest{
	
	@Autowired
	PersonInfoDao personInfoDao;
	
	@Test
	public void testInsertPersonInfo() {
		PersonInfo personInfo=new PersonInfo();
		personInfo.setName("花花");
		personInfo.setGender("女");
		personInfo.setEnableStatus(1);
		personInfo.setCreateTime(new Date());
		personInfo.setLastEditTime(new Date());
		personInfo.setCustomerFlag(1);
		personInfo.setShopOwnerFlag(1);
		personInfo.setAdminFlag(1);
		
		int effectedNum=personInfoDao.insertPersonInfo(personInfo);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryPersonInfoById() {
		long userId=12L;
		PersonInfo personInfo=personInfoDao.queryPersonInfoById(userId);
		System.out.println(personInfo.getName());
		
	}

}
