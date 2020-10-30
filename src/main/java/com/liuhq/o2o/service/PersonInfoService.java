package com.liuhq.o2o.service;

import com.liuhq.o2o.entity.PersonInfo;

public interface PersonInfoService {
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(long userId);

}
