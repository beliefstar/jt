package com.jtboot.web.controller;

import com.jtboot.web.pojo.Item;
import com.jtboot.web.pojo.ItemDesc;
import com.jtboot.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    public String findItems(@PathVariable Long itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        ItemDesc desc = itemService.findItemDescById(itemId);

        model.addAttribute("item", item);
        model.addAttribute("itemDesc", desc);
        return "item";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("name", "zhenxin");
        return "test";
    }
}
