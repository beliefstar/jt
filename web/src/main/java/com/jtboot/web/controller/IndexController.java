package com.jtboot.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.constant.ResultConstant;
import com.jtboot.common.exception.NoLoginException;
import com.jtboot.common.exception.ServiceException;
import com.jtboot.common.service.HttpClientService;
import com.jtboot.common.service.RedisService;
import com.jtboot.common.util.CookieUtils;
import com.jtboot.web.constant.CookieContant;
import com.jtboot.web.pojo.Cart;
import com.jtboot.web.pojo.User;
import com.jtboot.web.service.CartService;
import com.jtboot.web.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private RedisService redisService;

    @Qualifier("tempCartServiceImpl")
    @Autowired
    private CartService tempCartService;

    @RequestMapping({"/index", "/"})
    public String index(HttpServletRequest request, HttpServletResponse response) {

        String cookie = CookieUtils.getCookie(request, CookieContant.TEMP_CART_NAME);
        if (cookie != null) {
            try {
                User user = UserThreadLocal.get();
                List<Cart> cartList = tempCartService.findCartList(Long.valueOf(cookie));
                String url = "http://cart.jt.com/cart/sycCart/" + user.getId();
                try {
                    String string = objectMapper.writeValueAsString(cartList);
                    String s = httpClientService.doPostJson(url, string);
                    CookieUtils.deleteCookie(response, CookieContant.TEMP_CART_NAME);
                    String key = CookieContant.TEMP_CART_NAME + cookie;
                    redisService.del(key);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException(ResultConstant.OPERATION_FAIL);
                }
            } catch (NoLoginException e) {
//                e.printStackTrace();
            }
        }

        return "index";
    }

}
