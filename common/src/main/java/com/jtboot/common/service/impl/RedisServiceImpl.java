package com.jtboot.common.service.impl;

import com.jtboot.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, Integer timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean has(String key) {
        redisTemplate.hasKey(key);
        return false;
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    @Override
    public boolean expire(String key, int expire) {
        redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
        return false;
    }

    @Override
    public void del(Collection<String> keys) {
        redisTemplate.delete(keys);
    }
}
