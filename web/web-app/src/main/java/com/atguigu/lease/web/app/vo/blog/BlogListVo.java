package com.atguigu.lease.web.app.vo.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "博客列表展示信息")
public class BlogListVo{

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

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "当前用户是否点赞")
    private Boolean isLiked = false;
}
