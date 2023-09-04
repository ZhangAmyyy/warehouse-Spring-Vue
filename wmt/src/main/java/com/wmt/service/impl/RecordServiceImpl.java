package com.wmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmt.entity.Record;
import com.wmt.mapper.RecordMapper;
import com.wmt.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Resource
    private RecordMapper recordMapper;
    @Override
    public IPage pageCus(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.pageCus(page,wrapper);
    }
}
