package com.jtboot.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.service.HttpClientService;
import com.jtboot.common.service.RedisService;
import com.jtboot.common.exception.ServiceException;
import com.jtboot.web.pojo.Item;
import com.jtboot.web.pojo.ItemDesc;
import com.jtboot.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private RedisService jedisCluster;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Item findItemById(Long itemId) {
        String key = "ITEM_" + itemId;
        String json = jedisCluster.get(key);

        try {
            if (StringUtils.isEmpty(json)) {
                String s = httpClientService.doGet("http://manage.jt.com/item/" + itemId);

                jedisCluster.set(key, s);

                return objectMapper.readValue(s, Item.class);
            } else {
                return objectMapper.readValue(json, Item.class);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new ServiceException("数据获取失败！" + e.getMessage());
        }
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        String url = "http://manage.jt.com/item/desc/" + itemId;

        try {

            String s = httpClientService.doGet(url);

            return objectMapper.readValue(s, ItemDesc.class);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new ServiceException("数据获取失败！" + e.getMessage());
        }
    }
}
