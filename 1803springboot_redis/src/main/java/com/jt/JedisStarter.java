package com.jt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedisPool;

@SpringBootApplication
@RestController
public class JedisStarter {

	public static void main(String[] args) {
		SpringApplication.run(JedisStarter.class, args);
	}

	@Autowired
	private JedisCluster cluster;

	@RequestMapping("cluster")
	public String clusterSetGet(String key, String value) {
		cluster.set(key, value);
		return cluster.get(key);
	}
}


