package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("talk_info")
public class TalkInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "消息内容")
    @TableField(value = "content")
    private String content;

    @Schema(description = "发送用户ID")
    @TableField(value = "sender_id")
    private Long senderId;

    @Schema(description = "接收用户ID")
    @TableField(value = "receiver_id")
    private Long receiverId;

    @Schema(description = "是否已读")
    @TableField(value = "is_read")
    private Integer isRead;

    @Schema(description = "发送时间")
    @TableField(value = "create_time")
    private Date createTime;

}