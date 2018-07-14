package com.jtboot.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.exception.NoLoginException;
import com.jtboot.common.util.CookieUtils;
import com.jtboot.common.vo.SysResult;
import com.jtboot.web.constant.CookieContant;
import com.jtboot.web.pojo.Cart;
import com.jtboot.web.pojo.User;
import com.jtboot.web.service.CartService;
import com.jtboot.web.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping({"/cart", "/service/cart"})
public class CartController {

    private static Long defaultUID = 9L;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Qualifier("cartServiceImpl")
    @Autowired
    private CartService cartService;

    @Qualifier("tempCartServiceImpl")
    @Autowired
    private CartService tempCartService;

    @GetMapping("/show")
    public String toCart(HttpServletRequest request, ModelMap modelMap) {
        List<Cart> cartList = null;
        try {
            User user = UserThreadLocal.get();
            cartList = cartService.findCartList(user.getId());

        } catch (NoLoginException e) {
            log.info("未登录，使用临时购物车");
            String cookie = CookieUtils.getCookie(request, CookieContant.TEMP_CART_NAME);
            if (cookie != null) {
                cartList = tempCartService.findCartList(Long.valueOf(cookie));
            }
        }

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
    public SysResult updateNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request) {

        try {
            User user = UserThreadLocal.get();
            cartService.updateNum(user.getId(), itemId, num);
            return SysResult.oK();
        } catch (NoLoginException e) {
            log.info("未登录，使用临时购物车");
            String cookie = CookieUtils.getCookie(request, CookieContant.TEMP_CART_NAME);
            if (cookie != null) {
                tempCartService.updateNum(Long.valueOf(cookie), itemId, num);
            }
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "更新失败");
        }
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId, HttpServletRequest request) {
        try {
            User user = UserThreadLocal.get();

            cartService.deleteItem(user.getId(), itemId);
        } catch (NoLoginException e) {
            log.info("未登录，使用临时购物车");
            String cookie = CookieUtils.getCookie(request, CookieContant.TEMP_CART_NAME);
            if (cookie != null) {
                tempCartService.deleteItem(Long.valueOf(cookie), itemId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart/show.html";
    }

    @RequestMapping("/add/{itemId}")
    public String addItem(@PathVariable Long itemId, Cart cart, HttpServletRequest request, HttpServletResponse response) {
        cart.setItemId(itemId);
        try {
            User user = UserThreadLocal.get();
            cart.setUserId(user.getId());

            cartService.addItem(cart);
        } catch (NoLoginException e) {
            log.info("未登录，使用临时购物车");
            String cookie = CookieUtils.getCookie(request, CookieContant.TEMP_CART_NAME);
            if (cookie != null) {
                cart.setUserId(Long.valueOf(cookie));
            } else {
                cart.setUserId(null);
            }
            tempCartService.addItem(cart);
            if (cookie == null && cart.getUserId() != null) {
                CookieUtils.setCookie(response, CookieContant.TEMP_CART_NAME, Long.toString(cart.getUserId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/cart/show.html";
    }
}
