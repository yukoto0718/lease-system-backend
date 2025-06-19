package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.BlogGraphInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface BlogGraphInfoMapper extends BaseMapper<BlogGraphInfo> {
    /**
     * 根据博客ID查询所有图片
     */
    List<BlogGraphInfo> selectGraphsByBlogId(Long blogId);
}
