package com.jtboot.common.service;

import java.util.Collection;
import java.util.Map;

public interface RedisService {

    void set(String key, String value);

    void set(String key, String value, Integer timeout);

    void del(String key);

    boolean has(String key);

    String get(String key);

    <T> T get(String key, Class<T> clsType);

    boolean expire(String key, int expire);

    void del(Collection<String> keys);

    void hset(String key, String field, String value);

    void hset(String key, String field, String value, Integer timeout);

    void hdel(String key, String field);

    String hget(String key, String field);

    <T> T hget(String key, String field, Class<T> clsType);

    Map<Object, Object> hgetAll(String key);
}
