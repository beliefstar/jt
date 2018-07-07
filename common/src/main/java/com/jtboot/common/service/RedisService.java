package com.jtboot.common.service;

import java.util.Collection;

public interface RedisService {

    void set(String key, String value);

    void set(String key, String value, Integer timeout);

    void del(String key);

    boolean has(String key);

    String get(String key);

    boolean expire(String key, int expire);

    void del(Collection<String> keys);
}
