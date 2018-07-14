package com.jtboot.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.service.RedisService;
import com.jtboot.web.constant.CookieContant;
import com.jtboot.web.pojo.Cart;
import com.jtboot.web.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TempCartServiceImpl implements CartService {

    private static Integer timeout = 1000 * 60 * 60 * 2;//2小时

    @Autowired
    private RedisService redisService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Cart> findCartList(Long userId) {
        String key = CookieContant.TEMP_CART_NAME + userId;
        Map<Object, Object> map = redisService.hgetAll(key);
        if (map != null && map.size() != 0) {
            return map.values().stream().map(item -> {
                try {
                    return objectMapper.readValue((String) item, Cart.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void updateNum(Long userId, Long itemId, Integer num) {
        String key = CookieContant.TEMP_CART_NAME + userId;
        Cart hget = redisService.hget(key, Long.toString(itemId), Cart.class);
        if (hget != null) {
            hget.setNum(num);
        }
        addItem(hget);
    }

    @Override
    public void deleteItem(Long userId, Long itemId) {
        String key = CookieContant.TEMP_CART_NAME + userId;
        redisService.hdel(key, Long.toString(itemId));
    }

    @Override
    public void addItem(Cart cart) {
        if (cart.getUserId() == null) {
            cart.setUserId(getUserKey());
        }
        try {
            String value = objectMapper.writeValueAsString(cart);
            String key = CookieContant.TEMP_CART_NAME + cart.getUserId();
            redisService.hset(key, Long.toString(cart.getItemId()), value, timeout);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private synchronized Long getUserKey() {
        return System.currentTimeMillis();
    }
}
