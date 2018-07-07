package com.jtboot.manager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.service.RedisService;
import com.jtboot.common.vo.ItemCatData;
import com.jtboot.common.vo.ItemCatResult;
import com.jtboot.common.vo.ServiceException;
import com.jtboot.manager.mapper.ItemCatMapper;
import com.jtboot.manager.pojo.ItemCat;
import com.jtboot.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    //    @Autowired
//    private RedisServiceBySentinel redisService;

    @Autowired
    private RedisService redisService;

    /**
     * 使用通用Mapper(JPA),传入的对象最终充当了查询的where条件
     * select * from tb_item_cat where id = 100 and status = 1
     *
     * 总结:ItemCat对象会将不为Null的属性充当where条件
     * /如果需要添加查询条件
     * 为对象的属性赋值即可!!!
     *
     */
    @Override
    public ItemCat findItemCatById(Long itemId) {
        return itemCatMapper.selectByPrimaryKey(itemId);//.select(itemCat);
    }

    @Override
    public List<ItemCat> findItemCatByParentId(Long parentId) {

        String key = "ITEM_CAT_" + parentId;

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String s = redisService.get(key);
            if (StringUtils.isEmpty(s)) {
                ItemCat item = new ItemCat();
                item.setParentId(parentId);
                List<ItemCat> list = itemCatMapper.select(item);

                String json = objectMapper.writeValueAsString(list);
                redisService.set(key, json);
                return list;
            } else {
                ItemCat[] itemCats = objectMapper.readValue(s, ItemCat[].class);
                return Arrays.asList(itemCats);
            }
        } catch (Exception e) {
            throw new ServiceException("数据获取失败！" + e.getMessage());
        }
    }

    @Override
    public ItemCatResult findItemCatAll() {
        String itemCat_name = "ITEM_CAT_ALL";
        String s = redisService.get(itemCat_name);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (StringUtils.isEmpty(s)) {
                ItemCatResult itemCatResult = findItemCatAllByResult();

                String json = objectMapper.writeValueAsString(itemCatResult);
                redisService.set(itemCat_name, json);
                return itemCatResult;
            } else {
                return objectMapper.readValue(s, ItemCatResult.class);
            }
        } catch (Exception e) {
            throw new ServiceException("数据获取失败！" + e.getMessage());
        }
    }

    private ItemCatResult findItemCatAllByResult() {
        ItemCat temp = new ItemCat();
        temp.setStatus(1);

        List<ItemCat> itemCatAll = itemCatMapper.select(temp);

        Map<Long, List<ItemCat>> itemCatMap = new HashMap<>();
        for (ItemCat item: itemCatAll) {
            if (itemCatMap.containsKey(item.getParentId())) {
                itemCatMap.get(item.getParentId()).add(item);
            } else {
                List<ItemCat> list = new ArrayList<>();
                list.add(item);
                itemCatMap.put(item.getParentId(), list);
            }
        }

        List<ItemCatData> list = new ArrayList<>();

        for (ItemCat item: itemCatMap.get(0L)) {
            ItemCatData itemCatData = new ItemCatData();
            String url = "/products/" + item.getId() + ".html";
            String name = "<a href=\"" + url + "\">" + item.getName() + "</a>";
            itemCatData.setUrl(url);
            itemCatData.setName(name);

            List<ItemCatData> list2 = new ArrayList<>();
            for (ItemCat item2: itemCatMap.get(item.getId())) {
                ItemCatData itemCatData2 = new ItemCatData();
                String url2 = "/products/" + item2.getId();
                String name2 = item2.getName();
                itemCatData2.setUrl(url2);
                itemCatData2.setName(name2);

                List<String> list3 = new ArrayList<>();
                for(ItemCat item3: itemCatMap.get(item2.getId())) {
                    list3.add("/products/" + item3.getId() + "|" + item3.getName());
                }
                itemCatData2.setItems(list3);

                list2.add(itemCatData2);
            }

            itemCatData.setItems(list2);
            list.add(itemCatData);
        }

        ItemCatResult rel = new ItemCatResult();
        rel.setItemCats(list);
        return rel;
    }
}
