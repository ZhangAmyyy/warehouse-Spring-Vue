package com.wmt.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wmt.entity.Storage;

public interface StorageService extends IService<Storage> {
    IPage pageCus(IPage<Storage> page, Wrapper wrapper);
}
