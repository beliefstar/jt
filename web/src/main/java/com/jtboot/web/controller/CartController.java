package com.jtboot.web.controller;

import com.jtboot.common.vo.SysResult;
import com.jtboot.web.pojo.Cart;
import com.jtboot.web.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/cart", "/service/cart"})
public class CartController {

    private static Long defaultUID = 9L;

    @Autowired
    private CartService cartService;

    @GetMapping("/show")
    public String toCart(ModelMap modelMap) {
        //todo
        List<Cart> cartList = cartService.findCartList(defaultUID);
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        modelMap.put("cartList", cartList);
        return "cart";
    }

    /**
     * 更改数量
     * @param itemId
     * @param num
     * @return
     */
    @PostMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateNum(@PathVariable Long itemId, @PathVariable Integer num) {
        try {
            //todo
            cartService.updateNum(defaultUID, itemId, num);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "更新失败");
        }
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId) {
        //todo
        try {
            cartService.deleteItem(defaultUID, itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cart";
    }

    @RequestMapping("/add/{itemId}")
    public String addItem(@PathVariable Long itemId, Cart cart) {
        cart.setItemId(itemId);
        cart.setUserId(defaultUID);
        //todo
        try {
            cartService.addItem(cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart/show.html";
    }
}
