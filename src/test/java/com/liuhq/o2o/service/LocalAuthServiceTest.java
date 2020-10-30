package com.liuhq.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.dto.LocalAuthExecution;
import com.liuhq.o2o.entity.LocalAuth;
import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.enums.WechatAuthStateEnum;
import com.liuhq.o2o.utils.MD5;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthServiceTest{
	
	@Autowired
	private LocalAuthService localAuthService;
	
	@Test
	public void testBindLocalAuth() {
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		String userName = "testusername";
		String password = "testpassword";
		personInfo.setUserId(1L);
		localAuth.setUserId(1L);
		localAuth.setPersonInfo(personInfo);
		localAuth.setUserName(userName);
		localAuth.setPassword(password);
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(), lae.getState());
		localAuth =localAuthService.getLocalAuthByUserId(personInfo.getUserId());
		System.out.println(localAuth.getUserName());
		System.out.println(localAuth.getPassword());
		
	}
	@Test
	public void testUpdatePwd() {
		long userId=8L;
		String userName = "testbind";
		String password = "123456";
		String newPassword ="1234";
		LocalAuthExecution lae = localAuthService.modifyLocalAuth(userId, userName, password, newPassword);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(), lae.getState());
		LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, newPassword);
		System.out.println(localAuth.getUserName());
		
	}
	@Test
	public void testGetLogin() {
		LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd("test", MD5.getMd5("123456"));
		System.out.println(localAuth.getUserName());
	}

}
