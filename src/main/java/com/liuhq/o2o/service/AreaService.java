package com.liuhq.o2o.service;

import java.util.List;

import com.liuhq.o2o.entity.Area;

public interface AreaService {
	
	public static final String AREALISTKEY = "arealist";
	/**
	 * 
	 * @return
	 */
      List<Area> getAreaList();
}
