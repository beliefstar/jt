package com.jtboot.cart.pojo;

import com.jtboot.common.po.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_cart")
@Data
public class Cart extends BasePojo {
    private static final long serialVersionUID = -3635996177364402694L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long itemId;

    private String itemTitle;

    private String itemImage;

    private Long itemPrice;

    private Integer num;

}
