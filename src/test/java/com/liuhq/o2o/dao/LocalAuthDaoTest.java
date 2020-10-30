package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.liuhq.o2o.entity.LocalAuth;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthDaoTest{
	
	@Autowired
	LocalAuthDao localAuthDao;
	
//	@Test
	public void testInsert() {
		LocalAuth localAuth = new LocalAuth();
		localAuth.setLocalAuthId(8L);
		localAuth.setUserId(12L);
		localAuth.setUserName("test1");
		localAuth.setPassword("123456");
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		int effectedNum=localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1, effectedNum);
	}
	
   @Test
   public void testQueryLocalAuthByUserNameAndPwd() {
	   LocalAuth localAuth=localAuthDao.queryLocalAuthByUserNameAndPwd("testbind", "s05bse6q2qlb9qblls96s592y55y556s");
	   System.out.println(localAuth.getUserId());
	   System.out.println(localAuth.getPersonInfo().getName());
	   
   }
   
   @Test
   public void testQueryLocalAuthByUserId() {
	   LocalAuth localAuth=localAuthDao.queryLocalAuthByUserId(12L);
	   System.out.println(localAuth.getUserName());
   }
   
   @Test
   public void testUpdatePassword() {
	   Date now=new Date();
	   int effectedNum=localAuthDao.updateLocalAuth(8L,"testbind", "1234", "123456", now);
	   assertEquals(1, effectedNum);
	   LocalAuth localAuth=localAuthDao.queryLocalAuthByUserId(8L);
	   System.out.println(localAuth.getPassword());
	   
   }
   
}
