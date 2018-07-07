package com.jtboot.manager.pojo;

import com.jtboot.common.po.BasePojo;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "tb_item")
@Data
public class Item extends BasePojo {
    private static final long serialVersionUID = 7233111883262804027L;
    /**
     * 商品ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品卖点信息
     */
    private String sellPoint;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 二维码信息
     */
    private String barcode;

    /**
     * 图片信息
     */
    private String image;

    /**
     * 分类ID
     */
    private Long cid;

    /**
     * 状态值
     */
    private Integer status;

}
