package com.liuhq.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuhq.o2o.cache.JedisUtil;
import com.liuhq.o2o.dao.AreaDao;
import com.liuhq.o2o.entity.Area;
import com.liuhq.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	
    @Autowired
	private AreaDao areaDao;
    
    @Autowired
    private JedisUtil.Strings jedisStrings;
    
	@Autowired
	private JedisUtil.Keys jedisKeys;
	
	private static String AREALISTKEY = "arealist";
	
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	@Override
	public List<Area> getAreaList() {
		// TODO Auto-generated method stub
		String key = AREALISTKEY;
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (!jedisKeys.exists(key)) {
			areaList = areaDao.queryArea();
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(areaList);
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			    throw new RuntimeException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			    throw new RuntimeException(e.getMessage());
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			    throw new RuntimeException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			    throw new RuntimeException(e.getMessage());
			}
		}
		return areaList;
	}

}
