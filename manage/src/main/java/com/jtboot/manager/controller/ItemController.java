package com.jtboot.manager.controller;

import com.jtboot.common.vo.EasyUIResult;
import com.jtboot.common.vo.SysResult;
import com.jtboot.manager.pojo.Item;
import com.jtboot.manager.pojo.ItemDesc;
import com.jtboot.manager.service.ItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private static final Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Item> getItemAll() {
        return itemService.findItemAll();
    }

    @RequestMapping("/query")
    @ResponseBody
    public EasyUIResult findItemByPage(Integer page, Integer rows) {
        return itemService.findItemByPage(page, rows);
    }

    @RequestMapping("/save")
    @ResponseBody
    public SysResult save(Item item, String desc) {
        try {
            itemService.saveItem(item, desc);
            return SysResult.oK();
        } catch (Exception e) {
            return SysResult.build(201, "添加失败");
        }
    }

    @RequestMapping("/query/item/desc/{itemId}")
    @ResponseBody
    public SysResult desc(@PathVariable Long itemId) {
        try {
            ItemDesc item = itemService.findItemDescById(itemId);
            return new SysResult(item);
        } catch (Exception e) {
            return SysResult.build(201, "获取失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public SysResult update(Item item, String desc) {
        try {
            itemService.updateItem(item, desc);
            logger.info("更新成功");
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("更新失败");
            return SysResult.build(201, "更新失败");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public SysResult delete(String[] ids) {
        try {
            itemService.deleteItem(ids);
            logger.info("删除成功");
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除失败");
            return SysResult.build(201, "删除失败");
        }
    }

    /**
     * 下架
     * @param ids
     * @return
     */
    @RequestMapping("/instock")
    @ResponseBody
    public SysResult instock(String[] ids) {
        try {
            Integer status = 2;
            itemService.updateItemStatus(status, ids);
            logger.info("下架成功");
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("下架失败");
            return SysResult.build(201, "下架失败");
        }
    }

    /**
     * 上架
     * @param ids
     * @return
     */
    @RequestMapping("/reshelf")
    @ResponseBody
    public SysResult reshelf(String[] ids) {
        try {
            Integer status = 1;
            itemService.updateItemStatus(status, ids);
            logger.info("上架成功");
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("上架失败");
            return SysResult.build(201, "上架失败");
        }
    }

    @RequestMapping(value = "/cat/queryItemName", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String queryItemName(Integer itemId) {
        return itemService.findItemCatName(itemId);
    }
}
