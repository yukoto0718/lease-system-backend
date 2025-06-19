package com.atguigu.lease.web.app.vo.blog;

import com.atguigu.lease.model.entity.BlogInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "博客展示信息")
public class BlogDetailVo extends BlogInfo {

    @Schema(description = "博客ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "博客标题")
    private String title;

    @Schema(description = "博客内容")
    private String content;

    @Schema(description = "图片列表")
    private List<BlogGraphVo> imageList;

    @Schema(description = "点赞数")
    private Integer liked;

    @Schema(description = "评论数")
    private Integer comments;

    @Schema(description = "当前用户是否点赞")
    private Boolean isLiked;

    @Schema(description = "是否是自己发布的博客")
    private Boolean isOwnBlog;
    @Schema(description = "是否已关注博主")
    private Boolean isFollowed;

}
