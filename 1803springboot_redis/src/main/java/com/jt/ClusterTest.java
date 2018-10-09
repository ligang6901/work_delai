package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class ClusterTest {
	/*
	 * 测试集群jedis对象操作
	 */
	@Test
	public void clusterConnection(){
		//收集节点信息,整个集群,任意提供至少1个节点数量信息即可
		Set<HostAndPort> nodes=new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("10.9.142.29", 8000));
		nodes.add(new HostAndPort("10.9.142.29", 8001));
		//构造对象之前,需要先创建一个配置对象;
		GenericObjectPoolConfig config=
				new GenericObjectPoolConfig();
		config.setMaxIdle(8);
		config.setMaxTotal(200);
		//jedis操作分布式集群,使用的分片对象,操作redis-cluster
		//不需要分片计算,jedisCluster对象;构造原理在整合之后讨论
		JedisCluster cluster=new JedisCluster(nodes,1000,config);
		//for循环模拟海量数据生成
		for(int i=0;i<100;i++){
			String key="jt_key_"+i;
			String value="value_"+i;
			cluster.set(key, value);
			System.out.println(cluster.get(key));
		}
		//jedisCluster对象实现set,get等操作时,有没有连接池
	}
}










