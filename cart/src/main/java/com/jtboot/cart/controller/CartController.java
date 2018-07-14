package com.jtboot.cart.controller;

import com.jtboot.cart.pojo.Cart;
import com.jtboot.cart.service.CartService;
import com.jtboot.common.constant.ResultConstant;
import com.jtboot.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/query/{userId}")
    @ResponseBody
    public SysResult findAll(@PathVariable Long userId) {
        try {
            List<Cart> byUserId = cartService.findByUserId(userId);
            return SysResult.oK(byUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, ResultConstant.DATA_FIND_FAIL);
        }
    }

    //http://www.jt.com/service/cart/update/num/1,474,391,964/2
    @GetMapping("/update/num/{userId}/{itemId}/{num}")
    @ResponseBody
    public SysResult incrementNum(@PathVariable("userId") Long userId,
                                  @PathVariable("itemId") Long itemId,
                                  @PathVariable("num") Integer num) {
        try {
            Integer integer = cartService.updateNum(userId, itemId, num);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, ResultConstant.DATA_UPDATE_FAIL);
        }
    }

    @GetMapping("/delete/{userId}/{itemId}")
    @ResponseBody
    public SysResult deleteItem(@PathVariable("userId") Long userId,
                                @PathVariable("itemId") Long itemId) {
        try {
            cartService.delete(userId, itemId);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, ResultConstant.OPERATION_FAIL);
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public SysResult addItem(@RequestBody Cart cart) {
        try {
            cartService.add(cart);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, ResultConstant.OPERATION_FAIL);
        }
    }

    @PostMapping("/sycCart/{userId}")
    @ResponseBody
    public SysResult sycCart(@RequestBody List<Cart> list, @PathVariable Long userId) {
        cartService.sycCart(list, userId);
        return SysResult.oK();
    }
}
