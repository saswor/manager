package com.manage.framework.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.manage.common.utils.DateUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接
 * 
 * @author 思杰
 * 
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConnection {
	
	private Logger log = LoggerFactory.getLogger(RedisConnection.class);
//	private static String host = "localhost";
////	private static String host = "127.0.0.1";
//	private static int port = 6379;
////	private static int PORT = 6479;
//	private static int maxActive = 1024;
//	private static int maxIdle = 200;
//	private static int maxWait = 10000;
//	private static String password = "123456";
	
	private String host;
	
	private int port;
	
	private String password;
	
	@Value("${spring.redis.pool.max-active}")
	private int maxActive;
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.pool.min-idle}")
	private int minIdle;
	@Value("${spring.redis.pool.max-wait}")
	private int maxWait;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public void setHost(String host) {
		this.host = host;
	}

	private JedisPool jedisPool = null;

	/*
	 * 初始化redis连接池
	 */
	private void initPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(maxActive);// 最大连接数
			config.setMaxIdle(maxIdle);// 最大空闲连接数
			config.setMaxWaitMillis(maxWait);// 获取可用连接的最大等待时间
			config.setMinIdle(minIdle);
			jedisPool = new JedisPool(config, host, port);
		} catch (Exception e) {
			log.error("初始化连接池失败,时间:"+DateUtils.getTime(),e);
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取redis连接池
	 */
	public JedisPool getJedisPool() {
		try {
			if (jedisPool == null) {
				synchronized(RedisConnection.class) {
					if (jedisPool == null) {
						initPool();
					}
				}
				
			}
			return jedisPool;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取连接池失败,时间:"+DateUtils.getTime(),e);
			return null;
		}
	}

	/*
	 * 获取jedis实例
	 */
	public Jedis getJedis() {
		try {
			Jedis jedis = getJedisPool().getResource();
			jedis.auth(password);// 密码
			return jedis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}