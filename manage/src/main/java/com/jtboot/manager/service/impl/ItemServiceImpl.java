package com.jtboot.manager.service.impl;

import com.jtboot.common.service.RedisService;
import com.jtboot.common.vo.EasyUIResult;
import com.jtboot.manager.mapper.ItemDescMapper;
import com.jtboot.manager.mapper.ItemMapper;
import com.jtboot.manager.pojo.Item;
import com.jtboot.manager.pojo.ItemDesc;
import com.jtboot.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Autowired
    private RedisService jedisCluster;

    @Override
    public List<Item> findItemAll() {
        // todo findItemAll
        return itemMapper.select(null);//.findItemAll();
    }

    @Override
    public EasyUIResult findItemByPage(Integer page, Integer rows) {

        Integer total = itemMapper.selectCount(null);//.findItemCount();

        Integer begin = (page - 1) * rows;

        List<Item> items = itemMapper.findItemByPage(begin, rows);

        return new EasyUIResult(total, items);
    }

    @Override
    public String findItemCatName(Integer itemCatId) {
        return itemMapper.findItemCatName(itemCatId);
    }

    @Override
    public void saveItem(Item item, String desc) {
        Date date = new Date();

        item.setStatus(1);
        item.setCreated(date);
        item.setUpdated(date);
        itemMapper.insert(item);//插入操作  会数据回显，填补ID

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDescMapper.insert(itemDesc);

        //redis数据一致
        jedisCluster.del("ITEM_" + item.getId());
    }

    @Override
    public void updateItem(Item item, String desc) {
        Date date = new Date();

        item.setUpdated(date);
        itemMapper.updateByPrimaryKeySelective(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(date);
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);

        //redis数据一致
        jedisCluster.del("ITEM_" + item.getId());
    }

    @Override
    public Item findItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 删除操作时，先删除从表，再删主表
     * @param ids
     */
    @Override
    public void deleteItem(String[] ids) {
        //先删除从表
        itemDescMapper.deleteByIDS(ids);
        //删除主表
        itemMapper.deleteByIDS(ids);
    }

    @Override
    public void updateItemStatus(Integer status, String[] ids) {
        itemMapper.updateItemStatus(status, ids);
    }

    @Override
    public ItemDesc findItemDescById(Long id) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(id);
        List<ItemDesc> list = itemDescMapper.select(itemDesc);
        if (list.size() > 0) {
            itemDesc = list.get(0);
        }
        return itemDesc;
    }
}
