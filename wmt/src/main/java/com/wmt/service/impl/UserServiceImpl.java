package com.wmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmt.entity.User;
import com.wmt.mapper.UserMapper;
import com.wmt.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> listAll() {
        return userMapper.listAll();
    }

    @Override
    public IPage pageCus(IPage<User> page, Wrapper wrapper) {
        return userMapper.pageCus(page,wrapper);
    }

}
