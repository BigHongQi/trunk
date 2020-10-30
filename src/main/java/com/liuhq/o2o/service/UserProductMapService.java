package com.liuhq.o2o.service;

import com.liuhq.o2o.dto.UserProductMapExecution;
import com.liuhq.o2o.entity.UserProductMap;

public interface UserProductMapService {
    
	/**
	 * 用户消费信息列表
	 * @param productMap
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution  listUserProductMap(UserProductMap productMap,Integer pageIndex,Integer pageSize);
}
