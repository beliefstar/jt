package com.jtboot.manager.mapper;

import com.jtboot.manager.pojo.Item;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ItemMapper extends Mapper<Item> {

    void deleteByIDS(@Param("ids") String[] ids);

    List<Item> findItemByPage(
            @Param("begin") Integer begin,
            @Param("rows") Integer rows
    );

    String findItemCatName(@Param("itemCatId") Integer itemCatId);

    void updateItemStatus(@Param("status") Integer status, @Param("ids") String[] ids);

    List<Item> findItemAll();
}
