package com.liuhq.o2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.liuhq.o2o.dto.WechatAuthExecution;
import com.liuhq.o2o.entity.WechatAuth;



public interface WechatAuthService {

	/**
	 * 
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 *注册本平台的微信账号
	 * @param wechatAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException;

}
