package com.liuhq.o2o.dao;

import com.liuhq.o2o.entity.WechatAuth;

public interface WechatAuthDao {
    
	/**
	 * 
	 * @param openId
	 * @return
	 */
	WechatAuth queryWechatAuthByOpenId(String openId);
	
	/**
	 * 
	 * @param wechatAuth
	 * @return
	 */
	int insertWechatAuth(WechatAuth wechatAuth); 
	
}
