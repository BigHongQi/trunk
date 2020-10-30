package com.liuhq.o2o.service;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.entity.Area;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest{
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CacheService cacheService;
      
	  @Test
	  public void TestGetArea() throws Exception  {
		  List<Area> areaList=areaService.getAreaList();
		  for (Area area : areaList) {
			System.out.println(area);
		}
//		  cacheService.removeFromCache(areaService.AREALISTKEY);
		
	  }
}
