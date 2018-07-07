package com.jtboot.manager.service;

import com.jtboot.common.vo.ItemCatResult;
import com.jtboot.manager.pojo.ItemCat;

import java.util.List;

public interface ItemCatService {
    ItemCat findItemCatById(Long itemId);
    List<ItemCat> findItemCatByParentId(Long parentId);
    ItemCatResult findItemCatAll();
}
