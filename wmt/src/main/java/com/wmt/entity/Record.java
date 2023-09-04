package com.wmt.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Record {

    private int id;

    private int goods;

    @TableField("userId")
    private int userid;

    private int adminId;

    private int count;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createtime;

    private String remark;

    @TableField(exist = false)
    private String action;
}
