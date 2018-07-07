package com.jtboot.manager.controller.web;

import com.jtboot.manager.mapper.ItemDescMapper;
import com.jtboot.manager.mapper.ItemMapper;
import com.jtboot.manager.pojo.Item;
import com.jtboot.manager.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebItemController {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public Item findItemById(@PathVariable Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @RequestMapping("/item/desc/{itemId}")
    @ResponseBody
    public ItemDesc findItemDescById(@PathVariable Long itemId) {
        return itemDescMapper.selectByPrimaryKey(itemId);
    }
}
