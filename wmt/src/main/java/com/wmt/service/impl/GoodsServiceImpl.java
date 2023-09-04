package com.wmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmt.entity.Goods;
import com.wmt.mapper.GoodsMapper;
import com.wmt.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public IPage pageCus(IPage<Goods> page, Wrapper wrapper) {
        return goodsMapper.pageCus(page,wrapper);
    }
}
