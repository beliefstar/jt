package com.jtboot.manager.mapper;

import com.jtboot.manager.pojo.Item;
import com.jtboot.manager.pojo.ItemCat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemCatMapperTest {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void selectTest() {
//        System.out.println(dataSource.getClass().getName());
//
//        List<Item> items = itemMapper.selectAll();
//        System.out.println(items);
//        List<Item> itemAll = itemMapper.findItemAll();
//        System.out.println(itemAll);

        List<ItemCat> select = itemCatMapper.select(null);
        System.out.println(select);
    }

}