package com.liuhq.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuhq.o2o.cache.JedisUtil;
import com.liuhq.o2o.dao.HeadLineDao;
import com.liuhq.o2o.entity.HeadLine;
import com.liuhq.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    
	@Autowired
	private HeadLineDao headLineDao;
	
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;

	private static String HLLISTKEY = "headlinelist";

	private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);
	
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		// TODO Auto-generated method stub
		List<HeadLine> headLineList = null;
		ObjectMapper mapper = new ObjectMapper();
		String key = HLLISTKEY;
		if (headLineCondition.getEnableStatus() != null && headLineCondition != null) {
			key = key + "_" + headLineCondition.getEnableStatus();
		}
		if (!jedisKeys.exists(key)) {
			headLineList = headLineDao.queryHeadLine(headLineCondition);
			String jsonString = mapper.writeValueAsString(headLineList);
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			headLineList = mapper.readValue(jsonString, javaType);
		}
		return headLineList;
//		return headLineDao.queryHeadLine(headLineCondition);
	}

}
