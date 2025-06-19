package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "关注用户表")
@TableName(value = "blog_follow")
@Data
public class BlogFollow extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    @TableField(value = "user_id")
    private Long userId;

    @Schema(description = "关注的用户id")
    @TableField(value = "follow_user_id")
    private Long followUserId;

}
