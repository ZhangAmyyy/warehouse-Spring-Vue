package com.wmt.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wmt.entity.Goods;

public interface GoodsService extends IService<Goods> {
    IPage pageCus(IPage<Goods> page, Wrapper wrapper);
}
