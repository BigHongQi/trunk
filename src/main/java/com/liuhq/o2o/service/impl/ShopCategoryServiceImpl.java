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
import com.liuhq.o2o.dao.ShopCategoryDao;
import com.liuhq.o2o.entity.ShopCategory;
import com.liuhq.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	ShopCategoryDao shopCategoryDao;
	
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;

	private static String SCLISTKEY = "shopCategorylist";

	private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		// TODO Auto-generated method stub
		String key = SCLISTKEY;
		List<ShopCategory> shopCategoryList = null;
		ObjectMapper mapper = new ObjectMapper();
		if(shopCategoryCondition == null) {
			//若查询条件为空。则列出所有首页大类，即parentid为空的店铺类别
			key = key + "_allfirstlevel";
		}else if(shopCategoryCondition != null && shopCategoryCondition.getParentId() != null){
			//若parentid不为空，则列出该parentid下的所有子类别
			key = key + "_parent"+shopCategoryCondition.getParentId();
		}else if (shopCategoryCondition != null) {
			//列出所有子类别，不管其属于哪个类，都列出来
			key = key + "_allsecondlevel";
		}
		if (!jedisKeys.exists(key)) {
			
			// 当shopCategoryId不为空的时候，查询的条件会变为 where parent_id is null
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(shopCategoryList);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			    throw new RuntimeException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory()
					.constructParametricType(ArrayList.class,
							ShopCategory.class);
			try {
				shopCategoryList = mapper.readValue(jsonString, javaType);
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
		return shopCategoryList;
		//return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

}
