package com.jtboot.manager.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jtboot.common.po.BasePojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 通过通用Mapper的形式操作数据库
 *
 * 对象实体类 属性必须与相应数据表字段一一对应
 */
@Table(name = "tb_item_cat")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCat extends BasePojo {
    private static final long serialVersionUID = 6461502947096299750L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Long id;

    //@Column(name = "parent_id")
    private Long parentId;

    private String name;
    private Integer status;
    private Integer sortOrder;
    private Boolean isParent;
    private Date created;
    private Date updated;

    public ItemCat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public String getState() {
        return isParent ? "closed" : "open";
    }

    public String getText() {
        return name;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
