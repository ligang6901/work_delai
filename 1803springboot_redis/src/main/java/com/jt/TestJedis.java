package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestJedis {
	@Test
	public void jedisConnection() {
		// host IP地址 port 端口 Jedis(host,port);
		// 开启redis所在服务器防火墙
		// 开启端口 service iptables stop
		Jedis jedis = new Jedis("10.9.176.223", 6379);
		jedis.set("country", "china");

		// for 循环存储数据
		for (int i = 0; i < 50; i++) {
			String key = "1803jedis" + i;
			String value = "1803value" + i;
			jedis.set(key, value);
		}
		jedis.close();
	}

	// 测试缓存逻辑、
	@Test
	public void cacheLogic() {
		System.out.println("用户请求访问商品:1306272");
		// 到缓存判断数据 取数据,根据商品id生成自定义key
		String key = "ITEM_1306272";
		// 执行缓存逻辑
		Jedis jedis = new Jedis("10.9.75.225", 6379);
		if (jedis.exists(key)) {
			String result = jedis.get(key);
			System.out.println("商品信息：" + result);

		} else {
			// 模拟生成数据库商品信息
			String item = "{'id':1306272,'title':'电脑'}";
			System.out.println("商品信息" + item);
			jedis.set(key, item);
		}

	}

	// 自定义数据分片
	@Test
	public void autoShard() {
		Jedis jedis6376 = new Jedis("10.9.176.225", 6379);
		Jedis jedis6380 = new Jedis("10.9.176.225", 6380);
		Jedis jedis6381 = new Jedis("10.9.176.225", 6380);

		// 存储过程

		for (int i = 0; i < 100; i++) {
			Integer key = i;
			String value = "value" + i;
			// 分布式存储逻辑key<50
			if (key < 50) {
				jedis6376.set(key + "", value);
			} else if (key < 100) {
				jedis6380.set(key + "", value);
			} else {
				jedis6381.set(key + "", value);
			}
		}
		// 读数据
		int readInt = 61;

	}

	// hash 取余
	@Test
	public void hashShard() {

	}

	// jedis hash分片
	@Test
	public void jedishashShard() {
		// 分片对象底层实现了分片的逻辑,只需要收集所有
		// 的连接信息,自动完成分片计算,调方法和jedis一模一样
		// 收集redis节点信息
		List<JedisShardInfo> infoList = new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = new JedisShardInfo("10.9.17.153", 6379);
		JedisShardInfo info2 = new JedisShardInfo("10.9.17.153", 6380);
		JedisShardInfo info3 = new JedisShardInfo("10.9.17.153", 6381);
		infoList.add(info1);
		infoList.add(info2);
		infoList.add(info3);

		ShardedJedis sJedis = new ShardedJedis(infoList);
		sJedis.set("LHJLGHLJLHLH", "jt isgood very good");
		System.out.println(sJedis.get("LHJLGHLJLHLH"));
	}

	// jedis 分片连接池
	public void jedisPool() {
		// 利用节点信息配置信息：最大连接数，最小连接数，最大空闲数
		// 收集redis节点信息
		List<JedisShardInfo> infoList = new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = new JedisShardInfo("10.9.17.153", 6379);
		JedisShardInfo info2 = new JedisShardInfo("10.9.17.153", 6380);
		JedisShardInfo info3 = new JedisShardInfo("10.9.17.153", 6381);
		infoList.add(info1);
		infoList.add(info2);
		infoList.add(info3);
       
	}
}
