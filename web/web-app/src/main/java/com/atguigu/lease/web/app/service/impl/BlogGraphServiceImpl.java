package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.BlogGraphInfo;
import com.atguigu.lease.web.app.mapper.BlogGraphInfoMapper;
import com.atguigu.lease.web.app.service.BlogGraphService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlogGraphServiceImpl extends ServiceImpl<BlogGraphInfoMapper, BlogGraphInfo> implements BlogGraphService {
}
