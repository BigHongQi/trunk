package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.entity.WechatAuth;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthDaoTest {
	
	@Autowired
	WechatAuthDao wechatAuthDao;
	
	@Test
	public void testInsertWechatAuthInfo() {
		WechatAuth wechatAuth = new WechatAuth();
		wechatAuth.setUserId(12L);
		wechatAuth.setOpenId("dgagdrgdg");
		wechatAuth.setCreateTime(new Date());
		
		int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryWechatAuthInfo() {
		String openId = "dgagdrgdg";
		WechatAuth wechatAuth = wechatAuthDao.queryWechatAuthByOpenId(openId);
		System.out.println(wechatAuth.getPersonInfo().getName());
		
	}

}
