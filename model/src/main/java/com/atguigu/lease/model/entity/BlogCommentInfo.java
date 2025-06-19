package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("blog_comment_info")
public class BlogCommentInfo extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "博客id")
    @TableField(value = "blog_id")
    private Long blogId;

    @Schema(description = "用户id")
    @TableField(value = "user_id")
    private Long userId;

    @Schema(description = "评论内容")
    @TableField(value = "content")
    private String content;

    @Schema(description = "父评论id")
    @TableField(value = " parent_id")
    private Long parentId;

    @Schema(description = "回复用户的id")
    @TableField(value = "reply_user_id")
    private Long replyUserId;

}