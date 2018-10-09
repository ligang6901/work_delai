package com.jt.configuration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;


@Configuration
public class ClusterConfig {
	public static void main(String[] args) {
		System.out.println("lglgllglglglg");
		System.out.println("llllllllll");
	}
	//利用属性读取配置文件数据
	@Value("${spring.redis.cluster.nodes}")
	private String nodes;
	@Value("${spring.redis.pool.max-idle}")
	private Integer maxIdle;
	@Value("${spring.redis.pool.min-idle}")
	private Integer minIdle;
	@Value("${spring.redis.pool.max-total}")
	private Integer maxTotal;
	@Value("${spring.redis.pool.max-wait}")
	private Integer maxWait;
	
	//编写代码方法,将需要的内容这里成对象
	//传递给cluster的配置对象创建
	public GenericObjectPoolConfig getConfig(){
		//利用属性,完成配置对象config的创建
		GenericObjectPoolConfig config=new GenericObjectPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(maxWait);
		return config;
	}
	
	//利用配置对象创建JedisCluster对象
	//需要使用@Bean让框架管理,注入到该用到的位置使用
	@Bean
	public JedisCluster getCluster(){
		//整理节点信息
	 Set<HostAndPort> infoSet=new HashSet<HostAndPort>();
		//将节点信息解析成需要的内容,需要操作Spring的api
		String[] hosts=nodes.split(",");
		//10.0.0.0:8000是其中一个元素
		for (String hostAndPort : hosts) {
			String[] info = hostAndPort.split(":");
			infoSet.add(new HostAndPort(info[0],
					Integer.parseInt(info[1])));		
		}
		JedisCluster cluster=
				new JedisCluster(infoSet,getConfig());
		return cluster;
	}
	
	
	
	
	
	
	
	
	
	
	
}
