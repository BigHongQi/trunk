package com.liuhq.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.liuhq.o2o.entity.LocalAuth;

public interface LocalAuthDao {
  
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalAuthByUserNameAndPwd(@Param("userName")String userName,@Param("password")String password);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalAuthByUserId(long userId);
	
	/**
	 * 
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	
	/**
	 * 
	 * @param userId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId,
			@Param("userName") String userName,
			@Param("password") String password,
			@Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
	
	
}
