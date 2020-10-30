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

import com.liuhq.o2o.entity.Award;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardDaoTest {
	
	@Autowired
	private AwardDao awardDao;
	
	@Test
	public void testInsertAward() {
		long shopId = 19;
		
		Award award = new Award();
		award.setAwardName("测试一");
		award.setAwardImg("test1");
		award.setPoint(2);
		award.setPriority(1);
		award.setEnableStatus(1);
		award.setCreateTime(new Date());
		award.setLastEditTime(new Date());
		award.setShopId(shopId);
		
		int effectedNum = awardDao.insertAward(award);
		assertEquals(1, effectedNum);
		
		Award award2 = new Award();
		award2.setAwardName("测试二");
		award2.setAwardImg("test2");
		award2.setPoint(2);
		award2.setPriority(1);
		award2.setEnableStatus(1);
		award2.setCreateTime(new Date());
		award2.setLastEditTime(new Date());
		award2.setShopId(shopId);
		
		int effectedNum2 = awardDao.insertAward(award2);
		assertEquals(1, effectedNum2);
		
	}
	
	@Test
	public void testqueryAwardList() {
		Award award = new Award();
		List<Award> awList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(2, awList.size());
		int count = awardDao.queryAwardCount(award);	
		assertEquals(2, count);
		award.setAwardName("测试");
		awList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(2, awList.size());
		count = awardDao.queryAwardCount(award);	
		assertEquals(2, count);
	}
   
	@Test
	public void testqueryAwardByAwardId() {
		Award award = new Award();
		award.setAwardName("测试一");
		List<Award> awList = awardDao.queryAwardList(award, 0, 1);
		assertEquals(1, awList.size());
		
		Award award1 =awardDao.queryAwardByAwardId(awList.get(0).getAwardId());
		assertEquals("测试一",award1.getAwardName());
		
		
	}
	
	@Test
	public void testupdatetAward() {
		Award award = new Award();
		award.setAwardName("测试一");
		List<Award> awList = awardDao.queryAwardList(award, 0, 1);
		awList.get(0).setAwardName("第一个测试奖品");
		int effectedNum = awardDao.updatetAward(awList.get(0));
		assertEquals(1, effectedNum);
		Award award1 =awardDao.queryAwardByAwardId(awList.get(0).getAwardId());
		assertEquals("第一个测试奖品",award1.getAwardName());
		
	}
	
	@Test
	public void testdeleteAward() {
		Award award = new Award();
		award.setAwardName("测试");
		List<Award> awList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(2, awList.size());
		for (Award award2 : awList) {
			int effectedNum = awardDao.deleteAward(award2.getAwardId(), award2.getShopId());
			assertEquals(1, effectedNum);
		}
		
	}
}
