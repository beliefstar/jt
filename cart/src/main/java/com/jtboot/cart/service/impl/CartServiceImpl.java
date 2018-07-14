package com.jtboot.cart.service.impl;

import com.jtboot.cart.mapper.CartMapper;
import com.jtboot.cart.pojo.Cart;
import com.jtboot.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findByUserId(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartMapper.select(cart);
    }

    @Override
    public Integer updateNum(Long userId, Long itemId, Integer num) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(itemId);
        cart.setNum(num);
        return cartMapper.updateNum(cart);
    }

    @Override
    public Integer delete(Long userId, Long itemId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(itemId);

        return cartMapper.delete(cart);
    }

    @Override
    public Integer add(Cart cart) {
        Cart c = new Cart();
        c.setUserId(cart.getUserId());
        c.setItemId(cart.getItemId());
        c = cartMapper.selectOne(c);
        if (c != null) {
            Integer num = c.getNum() + cart.getNum();
            return updateNum(cart.getUserId(), cart.getItemId(), num);
        }
        cart.setId(null);
        return cartMapper.insert(cart);
    }

    @Override
    public void sycCart(List<Cart> carts, Long userId) {
        if (userId == null) return;
        final List<Cart> byUserId = findByUserId(userId);
        Iterator<Cart> iterator = carts.iterator();
        while (iterator.hasNext()) {
            Cart c = iterator.next();
            for (Cart item : byUserId) {
                if (item.getItemId().equals(c.getItemId())) {
                    updateNum(item.getUserId(), item.getItemId(), c.getNum());
                    iterator.remove();
                }
            }
        }
        for (Cart c : carts) {
            add(c);
        }
    }
}
