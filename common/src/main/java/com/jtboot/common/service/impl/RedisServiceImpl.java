package com.jtboot.common.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static ObjectMapper objectMapper = new ObjectMapper();

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
    public <T> T get(String key, Class<T> clsType) {
        String s = get(key);
        try {
            return objectMapper.readValue(s, clsType);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("redis读取数据失败，e={}", e);
        }
        return null;
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

    @Override
    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public void hset(String key, String field, String value, Integer timeout) {
        hset(key, field, value);
        expire(key, timeout);
    }

    @Override
    public void hdel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    @Override
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public <T> T hget(String key, String field, Class<T> clsType) {
        String hget = hget(key, field);
        try {
            return objectMapper.readValue(hget, clsType);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("redis读取数据失败，e={}", e);
        }
        return null;
    }

    @Override
    public Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
}
