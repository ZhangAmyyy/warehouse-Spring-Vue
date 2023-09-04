package com.wmt.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wmt.entity.User;


import java.util.List;

public interface UserService extends IService<User> {


    public List<User> listAll();

    IPage pageCus(IPage<User> page, Wrapper wrapper);
}
