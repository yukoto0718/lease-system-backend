package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.model.entity.BlogCommentInfo;
import com.atguigu.lease.model.entity.BlogInfo;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.app.mapper.BlogCommentInfoMapper;
import com.atguigu.lease.web.app.mapper.BlogInfoMapper;
import com.atguigu.lease.web.app.mapper.UserInfoMapper;
import com.atguigu.lease.web.app.service.IBlogCommentService;
import com.atguigu.lease.web.app.vo.blog.CommentSubmitVo;
import com.atguigu.lease.web.app.vo.blog.CommentVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentInfoMapper, BlogCommentInfo> implements IBlogCommentService {

    // 默认头像常量
    private static final String DEFAULT_AVATAR = "https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg";

    @Autowired
    private BlogInfoMapper blogInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private BlogCommentInfoMapper blogCommentInfoMapper;

    /**
     * 获取博客评论列表
     * @param blogId 博客ID
     * @return 评论树结构
     */
    @Override
    public List<CommentVo> getCommentListByBlogId(Long blogId) {
        // 直接使用映射查询获取一级评论，子评论通过嵌套查询自动加载
        List<CommentVo> rootComments = blogCommentInfoMapper.selectRootCommentsByBlogId(blogId);

        // 处理评论中可能为空的头像字段
        processAvatars(rootComments);

        return rootComments;
    }

    /**
     * 递归处理评论列表中的头像，确保所有评论都有头像
     * @param commentList 评论列表
     */
    private void processAvatars(List<CommentVo> commentList) {
        if (commentList == null || commentList.isEmpty()) {
            return;
        }

        for (CommentVo comment : commentList) {
            // 确保每个评论都有头像
            if (!StringUtils.hasText(comment.getAvatar())) {
                comment.setAvatar(DEFAULT_AVATAR);
            }

            // 递归处理子评论
            if (comment.getChildren() != null && !comment.getChildren().isEmpty()) {
                processAvatars(comment.getChildren());
            }
        }
    }

    /**
     * 保存评论
     * 简化版本，专注于业务逻辑而非数据转换
     * @param commentSubmitVo 评论提交VO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(CommentSubmitVo commentSubmitVo) {
        // 1. 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        // 2. 保存评论
        BlogCommentInfo commentInfo = new BlogCommentInfo();
        commentInfo.setBlogId(Long.valueOf(commentSubmitVo.getBlogId().toString()));
        commentInfo.setUserId(userId);
        commentInfo.setContent(commentSubmitVo.getContent());

        // 处理回复评论
        if (commentSubmitVo.getParentId() != null) {
            Long parentId = Long.valueOf(commentSubmitVo.getParentId().toString());
            commentInfo.setParentId(parentId);

            // 验证父评论是否存在
            BlogCommentInfo parentComment = this.getById(parentId);
            if (parentComment == null || parentComment.getIsDeleted() == 1) {
                throw new LeaseException(ResultCodeEnum.FAIL.getCode(), "父评论不存在或已被删除");
            }
        }

        if (commentSubmitVo.getReplyUserId() != null) {
            Long replyUserId = Long.valueOf(commentSubmitVo.getReplyUserId().toString());
            commentInfo.setReplyUserId(replyUserId);

            // 验证回复用户是否存在
            UserInfo replyUser = userInfoMapper.selectById(replyUserId);
            if (replyUser == null) {
                throw new LeaseException(ResultCodeEnum.FAIL.getCode(), "回复的用户不存在");
            }
        }

        this.save(commentInfo);
    }

    /**
     * 删除评论
     * @param commentId 评论ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeComment(Long commentId) {
        // 1. 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        // 2. 查询评论信息
        BlogCommentInfo commentInfo = this.getById(commentId);
        if (commentInfo == null) {
            throw new LeaseException(ResultCodeEnum.FAIL.getCode(), "评论不存在");
        }

        // 3. 获取评论及其子评论的总数（顺便验证权限）
        int affectedRows = baseMapper.countAffectedCommentRows(commentId, userId);
        if (affectedRows == 0) {
            throw new LeaseException(ResultCodeEnum.FAIL.getCode(), "无权删除该评论或评论不存在");
        }

        // 4. 逻辑删除评论及其子评论（带权限检查）
        baseMapper.logicalDeleteCommentWithPermissionCheck(commentId, userId);

        // 5. 安全更新博客评论计数（单次操作）
        baseMapper.updateBlogCommentCountSafely(commentInfo.getBlogId(), affectedRows);
    }
}