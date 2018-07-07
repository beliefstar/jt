package com.jtboot.web.pojo;

import com.jtboot.common.po.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Cart extends BasePojo {
    private static final long serialVersionUID = -3635996177364402694L;

    private Long id;

    private Long userId;

    private Long itemId;

    private String itemTitle;

    private String itemImage;

    private Long itemPrice;

    private Integer num;

}
