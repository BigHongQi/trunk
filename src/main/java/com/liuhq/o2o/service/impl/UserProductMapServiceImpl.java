package com.liuhq.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuhq.o2o.dao.UserProductMapDao;
import com.liuhq.o2o.dto.UserProductMapExecution;
import com.liuhq.o2o.entity.UserProductMap;
import com.liuhq.o2o.service.UserProductMapService;
import com.liuhq.o2o.utils.PageCalculator;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {
    @Autowired
    UserProductMapDao userProductMapDao;
    
	@Override
	public UserProductMapExecution listUserProductMap(UserProductMap productMap, Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		if(productMap != null && pageIndex != null && pageSize != null) {
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserProductMap>  productMaps = userProductMapDao.queryUserProductMapList(productMap, beginIndex, pageSize);
			 int count = userProductMapDao.queryUserProductMapCount(productMap);
			 UserProductMapExecution  se = new UserProductMapExecution();
			 se.setUserProductMapList(productMaps);
			 se.setCount(count);
			 return se;
		}else {
			return null;
		}
	}

}
