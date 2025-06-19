package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.BlogCommentInfo;
import com.atguigu.lease.web.app.vo.blog.CommentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 博客评论数据访问层，扩展了基础的BaseMapper功能
 * 新增多个针对评论的自定义查询方法
 */
public interface BlogCommentInfoMapper extends BaseMapper<BlogCommentInfo> {

    /**
     * 获取博客所有评论
     * @param blogId 博客ID
     * @return 评论列表
     */
    List<BlogCommentInfo> selectCommentsByBlogId(@Param("blogId") Long blogId);

    /**
     * 获取博客的一级评论列表，包含用户信息
     * @param blogId 博客ID
     * @return 一级评论VO列表，包含用户信息和子评论
     */
    List<CommentVo> selectRootCommentsByBlogId(@Param("blogId") Long blogId);

    /**
     * 获取子评论列表
     * @param id 父评论ID
     * @return 子评论VO列表，包含用户信息
     */
    List<CommentVo> selectChildComments(@Param("id") Long id);

    /**
     * 获取评论及其子评论的总数
     * @param commentId 评论ID
     * @return 评论总数（包括本身和子评论）
     */
    int countCommentAndChildren(@Param("commentId") Long commentId);

    /**
     * 批量逻辑删除评论和子评论
     * @param commentId 评论ID
     * @return 受影响的行数
     */
    int logicalDeleteCommentAndChildren(@Param("commentId") Long commentId);

    /**
     * 带权限检查的评论删除
     * @param commentId 评论ID
     * @param userId 当前用户ID
     * @return 影响的行数
     */
    int logicalDeleteCommentWithPermissionCheck(@Param("commentId") Long commentId, @Param("userId") Long userId);

    /**
     * 检查用户权限并返回受影响的评论计数
     * @param commentId 评论ID
     * @param userId 当前用户ID
     * @return 受影响的评论数
     */
    int countAffectedCommentRows(@Param("commentId") Long commentId, @Param("userId") Long userId);

    /**
     * 安全更新博客评论计数
     * @param blogId 博客ID
     * @param decrementBy 减少数量
     * @return 影响的行数
     */
    int updateBlogCommentCountSafely(@Param("blogId") Long blogId,
                                     @Param("decrementBy") int decrementBy);

}