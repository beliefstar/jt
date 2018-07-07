package com.jtboot.common.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BasePojo implements Serializable {
    private static final long serialVersionUID = -5823112755973171424L;

    private Date created;

    private Date updated;
}
