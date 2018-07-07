package com.jtboot.manager.pojo;

import com.jtboot.common.po.BasePojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_item_desc")
public class ItemDesc extends BasePojo {
    private static final long serialVersionUID = 32541296651786547L;

    @Id
    private Long itemId;//与ItemId一致

    private String itemDesc;

    public ItemDesc() {
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
