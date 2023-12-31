package com.wmt.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private int id;
    private String no;
    private String name;
    private String password;
    private int gender;
    private int roleId;
    private String phone;
    @TableField("isValid")
    private String isValid;
}
