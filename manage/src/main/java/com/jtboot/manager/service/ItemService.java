package com.jtboot.manager.service;

import com.jtboot.common.vo.EasyUIResult;
import com.jtboot.manager.pojo.Item;
import com.jtboot.manager.pojo.ItemDesc;

import java.util.List;

public interface ItemService {
    List<Item> findItemAll();

    EasyUIResult findItemByPage(Integer begin, Integer rows);

    String findItemCatName(Integer itemCatId);

    void saveItem(Item item, String desc);

    void updateItem(Item item, String desc);

    Item findItemById(Long itemId);

    void deleteItem(String[] ids);

    void updateItemStatus(Integer status, String[] ids);

    ItemDesc findItemDescById(Long id);
}
