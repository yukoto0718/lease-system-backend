package com.atguigu.lease.web.app.vo.blog;

import com.atguigu.lease.model.entity.BlogCommentInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "评论提交信息")
public class CommentSubmitVo extends BlogCommentInfo {

    @Schema(description = "博客ID")
    private Long blogId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "父评论ID")
    private Long parentId;

    @Schema(description = "回复用户ID")
    private Long replyUserId;
}
