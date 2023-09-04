package com.wmt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmt.entity.Menu;
import com.wmt.mapper.MenuMapper;
import com.wmt.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
