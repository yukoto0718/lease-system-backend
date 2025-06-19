package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.BlogInfo;
import com.atguigu.lease.web.app.vo.blog.BlogDetailVo;
import com.atguigu.lease.web.app.vo.blog.BlogListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface BlogInfoMapper extends BaseMapper<BlogInfo> {
    /**
     * 分页查询博客列表
     *
     * @param page 分页参数
     * @return 博客列表（包含用户信息和图片）
     */
    IPage<BlogListVo> selectBlogList(Page<BlogListVo> page);

    /**
     * 查询博客详情（包含图片）
     * 一次性查询博客和图片信息，避免N+1查询
     *
     * @param id 博客ID
     * @return 博客详情
     */
    BlogDetailVo selectBlogDetailWithImages(@Param("id") Long id);

    /**
     * 查询特定用户的博客列表
     * @param page 分页参数
     * @param userId 用户ID
     * @return 博客列表
     */
    IPage<BlogListVo> selectUserBlogList(Page<BlogListVo> page, Long userId);
}
