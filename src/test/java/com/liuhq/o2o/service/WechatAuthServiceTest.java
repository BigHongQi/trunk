package com.liuhq.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.dto.WechatAuthExecution;
import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.entity.WechatAuth;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest{
	
	@Autowired
	WechatAuthService wechatAuthService;
	
	@Test
	public void testInsertWechatAuth() {
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setCreateTime(new Date());
		personInfo.setName("测试3");
		personInfo.setEnableStatus(1);
		personInfo.setCustomerFlag(1);
		personInfo.setShopOwnerFlag(1);
		personInfo.setAdminFlag(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId("dgagdrgdg3");
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution effectedNum = wechatAuthService.register(wechatAuth);
		assertEquals(0, effectedNum.getState());
		wechatAuth = wechatAuthService.getWechatAuthByOpenId("dgagdrgdg3");
		System.out.println(wechatAuth.getPersonInfo().getName());
		
	}

}
