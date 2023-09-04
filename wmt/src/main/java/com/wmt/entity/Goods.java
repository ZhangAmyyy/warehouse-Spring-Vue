package com.wmt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Goods {

    private int id;


    private String name;


    private int storage;


    @TableField("goodsType")
    private int goodstype;


    private int count;


    private String remark;
}
