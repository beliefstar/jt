package com.jtboot.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.constant.ResultConstant;
import com.jtboot.common.service.HttpClientService;
import com.jtboot.common.exception.ServiceException;
import com.jtboot.common.vo.SysResult;
import com.jtboot.web.pojo.Cart;
import com.jtboot.web.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private HttpClientService httpClientService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Cart> findCartList(Long userId) {
        String url = "http://cart.jt.com/cart/query/" + userId;

        try {
            String cartList = httpClientService.doGet(url);
            if (StringUtils.isEmpty(cartList)) {
                return null;
            }
            SysResult carts = objectMapper.readValue(cartList, SysResult.class);
            if (carts.getStatus() == 200) {
                return (List<Cart>) carts.getData();
            }
            return null;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new ServiceException(ResultConstant.DATA_FIND_FAIL);
        }
    }

    @Override
    public void updateNum(Long userId, Long itemId, Integer num) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://cart.jt.com/cart/update/num/")
                .append(userId)
                .append("/")
                .append(itemId)
                .append("/")
                .append(num);

        String url = sb.toString();

        try {
            String s = httpClientService.doGet(url);
            if (StringUtils.isEmpty(s)) {
                throw new ServiceException(ResultConstant.DATA_UPDATE_FAIL);
            }
            SysResult sysResult = objectMapper.readValue(s, SysResult.class);
            if (sysResult.getStatus() != 200) {
                throw new ServiceException(ResultConstant.DATA_UPDATE_FAIL);
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new ServiceException(ResultConstant.DATA_UPDATE_FAIL);
        }
    }

    @Override
    public void deleteItem(Long userId, Long itemId) {
        String url = "http://cart.jt.com/cart/delete/" + userId + "/" + itemId;
        try {
            String s = httpClientService.doGet(url);
            if (StringUtils.isEmpty(s)) {
                throw new ServiceException(ResultConstant.OPERATION_FAIL);
            }
            SysResult sysResult = objectMapper.readValue(s, SysResult.class);
            if (sysResult.getStatus() != 200) {
                throw new ServiceException(ResultConstant.OPERATION_FAIL);
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new ServiceException(ResultConstant.OPERATION_FAIL);
        }
    }

    @Override
    public void addItem(Cart cart) {
        String url = "http://cart.jt.com/cart/add";
        try {
            String string = objectMapper.writeValueAsString(cart);
            String s = httpClientService.doPostJson(url, string);
            if (StringUtils.isEmpty(s)) {
                throw new ServiceException(ResultConstant.OPERATION_FAIL);
            }
            SysResult sysResult = objectMapper.readValue(s, SysResult.class);
            if (sysResult.getStatus() != 200) {
                throw new ServiceException(ResultConstant.OPERATION_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultConstant.OPERATION_FAIL);
        }
    }
}
