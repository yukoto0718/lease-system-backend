package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("talk_session")
public class TalkSession extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @TableField(value = "user_id")
    private Long userId;

    @Schema(description = "目标用户ID")
    @TableField(value = "target_user_id")
    private Long targetUserId;

    @Schema(description = "最后一条消息预览")
    @TableField(value = "last_message")
    private String lastMessage;

    @Schema(description = "未读消息数")
    @TableField(value = "unread_count")
    private Integer unreadCount;

    @Schema(description = "最后活跃时间")
    @TableField(value = "last_time")
    private Date lastTime;
}
