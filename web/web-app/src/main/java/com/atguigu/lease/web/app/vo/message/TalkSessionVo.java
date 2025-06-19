package com.atguigu.lease.web.app.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "聊天会话VO对象")
public class TalkSessionVo {

    @Schema(description = "会话ID")
    private Long id;

    @Schema(description = "目标用户ID")
    private Long targetUserId;

    @Schema(description = "目标用户昵称")
    private String targetNickname;

    @Schema(description = "目标用户头像")
    private String targetAvatar;

    @Schema(description = "最后一条消息内容")
    private String lastMessage;

    @Schema(description = "未读消息数量")
    private Integer unreadCount;

    @Schema(description = "最后消息时间")
    private Date lastTime;
}