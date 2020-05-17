package com.aaa.config;

import com.aaa.properties.RedisClusterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Qin
 * @Date 2020/5/14 21:43
 * @Version 1.0
 */
@Configuration
public class RedisClusterConfig {
    @Autowired
    private RedisClusterProperties redisClusterProperties;

    /**
     * @Description: 使用jedis连接redis服务器，并注入到spring容器中
     * @Param: []
     * @return: redis.clients.jedis.JedisCluster
     * @Author: Qin
     * @Date: 2020/5/17
     */
    @Bean
    public JedisCluster getJedisCluster() {
        String nodes = redisClusterProperties.getNodes();
        String[] nodeArr = nodes.split(",");
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        for (String node : nodeArr) {
            String[] split = node.split(":");
            HostAndPort hostAndPort = new HostAndPort(split[0], Integer.parseInt(split[1]));
            hostAndPorts.add(hostAndPort);
        }
        return new JedisCluster(hostAndPorts, redisClusterProperties.getCommandTimeout(), redisClusterProperties.getMaxAttempts());
    }

}
