package com.jtboot.web.service;

import com.jtboot.web.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findCartList(Long userId);
    void updateNum(Long userId, Long itemId, Integer num);
    void deleteItem(Long userId, Long itemId);
    void addItem(Cart cart);
}
