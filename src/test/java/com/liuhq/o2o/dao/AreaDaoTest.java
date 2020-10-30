package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.entity.Area;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AreaDaoTest {
	@Autowired
	private AreaDao areaDao;

	@Test
	public void testAInsertArea() throws Exception {
		Area area = new Area();
		area.setAreaName("区域1");
		area.setAreaDesc("区域1");
		area.setPriority(1);
		area.setCreateTime(new Date());
		area.setLastEditTime(new Date());
		int effectedNum = areaDao.insertArea(area);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryArea() throws Exception {
		List<Area> areaList = areaDao.queryArea();
		assertEquals(4, areaList.size());
	}

	@Test
	public void testCUpdateArea() throws Exception {
		Area area = new Area();
		long areaId=7;
		area.setAreaId(areaId);
		area.setAreaName("东南苑");
		area.setAreaDesc("东南苑");
		area.setPriority(1);
		area.setLastEditTime(new Date());
		int effectedNum = areaDao.updateArea(area);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testDDeleteArea() throws Exception {
		long areaId = -1;
		List<Area> areaList = areaDao.queryArea();
		for (Area myArea : areaList) {
			if ("区域1".equals(myArea.getAreaName())) {
				areaId = myArea.getAreaId();
			}
		}
		List<Long> areaIdList = new ArrayList<Long>();
		areaIdList.add(areaId);
		int effectedNum = areaDao.batchDeleteArea(areaIdList);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testDeleArea()throws Exception {
		long areaId=7;
		int effectedNum=areaDao.deleteArea(areaId);
		assertEquals(1, effectedNum);
	}
	
}
