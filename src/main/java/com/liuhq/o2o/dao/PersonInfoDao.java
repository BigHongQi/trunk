package com.liuhq.o2o.dao;

import com.liuhq.o2o.entity.PersonInfo;

public interface PersonInfoDao {
	/**
	 * 
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
	
	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);

}
