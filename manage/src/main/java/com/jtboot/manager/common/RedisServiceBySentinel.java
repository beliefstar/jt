package com.jtboot.manager.common;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;


public class RedisServiceBySentinel {
    @Autowired
    private JedisSentinelPool jedisSentinelPool;

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.get(key);
        } finally {
            jedisSentinelPool.returnResource(jedis);
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.set(key, value);
        } finally {
            jedisSentinelPool.returnResource(jedis);
        }
    }
}
