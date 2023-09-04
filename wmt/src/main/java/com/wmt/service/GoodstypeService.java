package com.wmt.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wmt.entity.Goodstype;

public interface GoodstypeService extends IService<Goodstype> {


    IPage pageCus(IPage<Goodstype> page, Wrapper wrapper);
}
