package com.wmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmt.entity.Goodstype;
import com.wmt.mapper.GoodstypeMapper;
import com.wmt.service.GoodstypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements GoodstypeService {

    @Resource
    private GoodstypeMapper goodstypeMapper;

    @Override
    public IPage pageCus(IPage<Goodstype> page, Wrapper wrapper) {
        return goodstypeMapper.pageCus(page,wrapper);
    }
}
