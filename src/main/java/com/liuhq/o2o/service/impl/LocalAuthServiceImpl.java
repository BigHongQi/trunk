package com.liuhq.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuhq.o2o.dao.LocalAuthDao;
import com.liuhq.o2o.dto.LocalAuthExecution;
import com.liuhq.o2o.entity.LocalAuth;
import com.liuhq.o2o.enums.LocalAuthStateEnum;
import com.liuhq.o2o.service.LocalAuthService;
import com.liuhq.o2o.utils.MD5;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
     
	@Autowired
	private LocalAuthDao localAuthDao;
	
	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
		// TODO Auto-generated method stub
		return localAuthDao.queryLocalAuthByUserNameAndPwd(userName, password);
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		// TODO Auto-generated method stub
		return localAuthDao.queryLocalAuthByUserId(userId);
	}

	@Override
	public LocalAuthExecution register(LocalAuth localAuth) throws RuntimeException {
		return null;
		// TODO Auto-generated method stub
    	
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException {
		// TODO Auto-generated method stub
		if (localAuth == null || localAuth.getPassword() == null || localAuth.getUserName() == null || localAuth.getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
    	//查询是不是绑定过平台账号
		LocalAuth tempAuth = localAuthDao.queryLocalAuthByUserId(localAuth.getUserId());
		if (tempAuth != null) {
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: "+ e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) {
		// TODO Auto-generated method stub
		if (userId != null && userName != null && password != null && newPassword != null && !password.equals(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(userId,userName, MD5.getMd5(password),MD5.getMd5(newPassword), new Date());
				if (effectedNum <= 0) {
					throw new RuntimeException("更新密码失败");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException("更新密码失败:" + e.toString());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}

}
