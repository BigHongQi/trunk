package com.liuhq.o2o.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liuhq.o2o.cache.JedisPoolWriper;
import com.liuhq.o2o.cache.JedisUtil;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
	@Value("${redis.hostname}")
	private String hostname;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.pool.maxActive}")
	private int maxTotal;
	@Value("${redis.pool.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.maxWait}")
	private int maxWaitMillis;
	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Autowired 
	private JedisPoolConfig jedisPoolConfig;
	@Autowired
	private JedisPoolWriper jedisPoolWriper;
	@Autowired
	private JedisUtil jedisUtil;
	
	/**
	 * Redis 连接池的设置-
	 * @return
	 */
	@Bean(name="jedisPoolConfig")
	public JedisPoolConfig createJedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//控制一个pool可分配多少个jedis实例
		jedisPoolConfig.setMaxTotal(maxTotal);
		//连接池中最多可空闲的maxIdle个连接，这里取值20
		jedisPoolConfig.setMaxIdle(maxIdle);
		// 最大等待时间 
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		//获取连接的时候检查有效性
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		return jedisPoolConfig;
		
	}
	/**
	 * 创建Redis连接池，并做相关配置 
	 * @return
	 */
	@Bean(name="jedisWritePool")
	public JedisPoolWriper createJedisPoolWriper() {
		JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig, hostname, port);
		return jedisPoolWriper;
		
	}
	/**
	 *  创建Redis工具类，封装好Redis的连接以进行相关的操作
	 * @return
	 */
	@Bean(name="jedisUtil")
	public JedisUtil createJedisUtil() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setJedisPool(jedisPoolWriper);
		return jedisUtil;
	}
	/**
	 * Redis的key操作
	 * @return
	 */
	@Bean(name="jedisKeys")
	public JedisUtil.Keys createJedisKeys(){
		JedisUtil.Keys keys = jedisUtil.new Keys();
		return keys;
	}
	/**
	 * Redis的String操作
	 * @return
	 */
	@Bean(name="jedisStrings")
	public JedisUtil.Strings createJedisStrings(){
		JedisUtil.Strings strings = jedisUtil.new Strings();
		return strings;
	}

}
