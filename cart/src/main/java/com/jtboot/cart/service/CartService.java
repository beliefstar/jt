package com.jtboot.cart.service;

import com.jtboot.cart.pojo.Cart;

import java.util.List;

public interface CartService {

    List<Cart> findByUserId(Long userId);

    Integer updateNum(Long userId, Long itemId, Integer num);

    Integer delete(Long userId, Long itemId);

    Integer add(Cart cart);

    void sycCart(List<Cart> cart, Long userId);
}
