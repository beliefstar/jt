package com.jtboot.manager.controller;

import com.jtboot.manager.pojo.ItemCat;
import com.jtboot.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/param/query/itemcatid/{itemId}")
    @ResponseBody
    public ItemCat findItemCat(@PathVariable("itemId") Long itemId) {
        return itemCatService.findItemCatById(itemId);
    }

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<ItemCat> findItemCatByParent(
            @RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return itemCatService.findItemCatByParentId(parentId);
    }
}
