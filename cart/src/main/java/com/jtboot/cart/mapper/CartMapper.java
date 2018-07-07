package com.jtboot.cart.mapper;

import com.jtboot.cart.pojo.Cart;
import tk.mybatis.mapper.common.Mapper;

public interface CartMapper extends Mapper<Cart> {
    Integer updateNum(Cart cart);
}
