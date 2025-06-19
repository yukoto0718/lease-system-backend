package com.atguigu.lease.web.app.vo.blog;

import com.atguigu.lease.model.entity.BlogCommentInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "评论展示信息")
public class CommentVo extends BlogCommentInfo {

    @Schema(description = "评论ID")
    private Long id;

    @Schema(description = "博客ID")
    private Long blogId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "父评论ID")
    private Long parentId;

    @Schema(description = "回复用户ID")
    private Long replyUserId;

    @Schema(description = "回复用户昵称")
    private String replyUserNickname;

    @Schema(description = "子评论列表")
    private List<CommentVo> children;
}
