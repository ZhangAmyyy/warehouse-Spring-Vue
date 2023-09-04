package com.wmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmt.entity.Storage;
import com.wmt.mapper.StorageMapper;
import com.wmt.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    @Resource
    private StorageMapper storageMapper;

    @Override
    public IPage pageCus(IPage<Storage> page, Wrapper wrapper) {
        return storageMapper.pageCus(page,wrapper);
    }
}
