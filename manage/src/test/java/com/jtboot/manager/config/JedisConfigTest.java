package com.jtboot.manager.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisConfigTest {

    @Autowired
    private ShardedJedisPool jedisPool;

    @Test
    public void shardedJedisPoolSet() {
        ShardedJedis resource = jedisPool.getResource();
        resource.set("zhenxin", "66996");
    }

    @Test
    public void shardedJedisPoolGet() {
        ShardedJedis resource = jedisPool.getResource();
        String zhenxin = resource.get("zhenxin");
        System.out.println(zhenxin);
    }
}