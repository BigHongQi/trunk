package com.liuhq.o2o.config.service;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * 对标Spring-service里面的transactionManager
 * 继承TransactionManagementConfigurer 是因为开启annotation-driven
 * @author RD-10
 *
 */
@Configuration
//使用注解@EnableTransactionManagement 开启事务支持
//在service上添加@Transactional便可
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer{
    
	@Autowired
	private DataSource dataSource;
	
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		// TODO Auto-generated method stub
		return new DataSourceTransactionManager(dataSource);
	}

}
