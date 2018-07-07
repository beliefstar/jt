package com.jtboot.manager.mapper;

import com.jtboot.manager.pojo.ItemDesc;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ItemDescMapper extends Mapper<ItemDesc> {
    void deleteByIDS(@Param("ids") String[] ids);
}
