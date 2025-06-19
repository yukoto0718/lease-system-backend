package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "博客信息表")
@TableName(value = "blog_info")
@Data
public class BlogInfo extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    @TableField(value = "user_id")
    private Long userId;

    @Schema(description = "标题")
    @TableField(value = "title")
    private String title;

    @Schema(description = "博客文字描述")
    @TableField(value = "content")
    private String content;

    @Schema(description = "点赞数量")
    @TableField(value = "liked")
    private Integer liked;

    @Schema(description = "评论数量")
    @TableField(value = "comments")
    private Integer comments;

}
