package com.liuhq.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuhq.o2o.dao.PersonInfoDao;
import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.service.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    
	@Autowired
	PersonInfoDao personInfoDao;
	
	@Override
	public PersonInfo getPersonInfoById(long userId) {
		// TODO Auto-generated method stub
		return personInfoDao.queryPersonInfoById(userId);
	}

}
