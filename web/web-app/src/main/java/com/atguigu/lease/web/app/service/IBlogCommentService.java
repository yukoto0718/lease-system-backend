package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.BlogCommentInfo;
import com.atguigu.lease.web.app.vo.blog.CommentSubmitVo;
import com.atguigu.lease.web.app.vo.blog.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBlogCommentService extends IService<BlogCommentInfo> {

    /**
     * 获取博客评论列表
     * @param blogId 博客ID
     * @return 评论列表
     */
    List<CommentVo> getCommentListByBlogId(Long blogId);

    /**
     * 保存评论
     * @param commentSubmitVo 评论信息
     */
    void saveComment(CommentSubmitVo commentSubmitVo);

    /**
     * 删除评论
     * @param commentId 评论ID
     */
    void removeComment(Long commentId);
}