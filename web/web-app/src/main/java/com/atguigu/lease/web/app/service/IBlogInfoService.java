package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.BlogInfo;
import com.atguigu.lease.web.app.vo.blog.BlogDetailVo;
import com.atguigu.lease.web.app.vo.blog.BlogListVo;
import com.atguigu.lease.web.app.vo.blog.BlogSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author yu
 * @description 针对表【blog_info(博客信息表)】的数据库操作Service
 * @createDate 2025-03-10
 */
public interface IBlogInfoService extends IService<BlogInfo> {
    void saveOrUpdateBlog(BlogSubmitVo blogSubmitVo);

    BlogDetailVo getBlogDetailById(Long id);

    IPage<BlogListVo> getBlogList(Page<BlogListVo> page);

    void likeBlog(Long id);

    /**
     * 获取特定用户的博客列表
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页的博客列表
     */
    IPage<BlogListVo> getUserBlogList(Page<BlogListVo> page, Long userId);

    /**
     * 获取用户博客空间信息
     * @param userId 用户ID
     * @return 用户空间信息
     */
    Map<String, Object> getUserSpaceInfo(Long userId);
}
