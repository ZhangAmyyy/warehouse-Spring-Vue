package com.wmt.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wmt.entity.Record;

public interface RecordService extends IService<Record> {
    IPage pageCus(IPage<Record> page, Wrapper wrapper);
}
