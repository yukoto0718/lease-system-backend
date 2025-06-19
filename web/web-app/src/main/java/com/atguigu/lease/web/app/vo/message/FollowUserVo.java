package com.atguigu.lease.web.app.vo.message;

import com.atguigu.lease.model.entity.BlogFollow;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "关注列表VO对象")
public class FollowUserVo extends BlogFollow {
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatarUrl;

    @Schema(description = "是否互相关注")
    private Boolean mutualFollow;
}
