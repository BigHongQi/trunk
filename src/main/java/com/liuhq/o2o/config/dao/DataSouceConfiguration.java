package com.liuhq.o2o.config.dao;

import java.beans.PropertyVetoException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liuhq.o2o.utils.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
//配置mybatis mapper的扫描路径
@MapperScan("com.liuhq.o2o.dao")
public class DataSouceConfiguration {
    
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	/**
	 * 生成与Spring-dao.xml对应的bean datasource
	 * @return
	 * @throws PropertyVetoException 
	 */
	@Bean(name="dataSource")
	public ComboPooledDataSource createDataSource() throws PropertyVetoException {
		//生成datasource实例
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		//驱动
		dataSource.setDriverClass(jdbcDriver);
		//usl
		dataSource.setJdbcUrl(jdbcUrl);
		//用户名
		dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
		//密码
		dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
		//c3p0连接池的私有属性
		//连接池最大线程数
		dataSource.setMaxPoolSize(30);
		//连接池最小线程数
		dataSource.setMaxPoolSize(10);
		//关闭连接后不自动commit
		dataSource.setAutoCommitOnClose(false);
		//获取连接超时时间
		dataSource.setCheckoutTimeout(10000);
		//当获取连接失败重试次数
		dataSource.setAcquireRetryAttempts(2);
		return dataSource;
		
	}
}
