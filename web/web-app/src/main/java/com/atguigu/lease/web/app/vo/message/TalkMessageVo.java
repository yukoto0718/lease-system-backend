package com.atguigu.lease.web.app.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "聊天消息VO对象")
public class TalkMessageVo{

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "发送者ID")
    private Long senderId;

    @Schema(description = "发送者昵称")
    private String senderNickname;

    @Schema(description = "发送者头像")
    private String senderAvatar;

    @Schema(description = "接收者ID")
    private Long receiverId;

    @Schema(description = "消息发送时间")
    private Date createTime;

    @Schema(description = "是否为当前用户发送的消息")
    private Boolean isSelf;
}