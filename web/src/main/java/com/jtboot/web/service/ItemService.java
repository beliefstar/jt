package com.jtboot.web.service;

import com.jtboot.web.pojo.Item;
import com.jtboot.web.pojo.ItemDesc;

public interface ItemService {
    Item findItemById(Long itemId);

    ItemDesc findItemDescById(Long itemId);
}
