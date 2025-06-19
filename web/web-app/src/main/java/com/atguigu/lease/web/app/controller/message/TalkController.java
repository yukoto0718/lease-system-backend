package com.atguigu.lease.web.app.controller.message;

import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.web.app.service.TalkService;
import com.atguigu.lease.web.app.vo.message.TalkMessageVo;
import com.atguigu.lease.web.app.vo.message.TalkSessionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "聊天接口")
@RestController
@RequestMapping("/app/talk")
public class TalkController {

    @Autowired
    private TalkService talkService;

    @Operation(summary = "获取聊天历史记录")
    @GetMapping("/history/{targetUserId}")
    public Result<List<TalkMessageVo>> getChatHistory(
            @Parameter(description = "目标用户ID", required = true)
            @PathVariable Long targetUserId,

            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Integer page,

            @Parameter(description = "每页大小", example = "20")
            @RequestParam(defaultValue = "20") Integer size
    ) {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        List<TalkMessageVo> messages = talkService.getChatHistory(userId, targetUserId, page, size);

        // 标记消息为已读
        // 注意参数顺序：userId是接收者，targetUserId是发送者
        talkService.markAsRead(userId, targetUserId);

        return Result.ok(messages);
    }
    @Operation(summary = "获取会话列表")
    @GetMapping("/sessions")
    public Result<IPage<TalkSessionVo>> getSessionList(
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Integer page,

            @Parameter(description = "每页大小", example = "20")
            @RequestParam(defaultValue = "20") Integer size,

            @Parameter(description = "搜索关键词")
            @RequestParam(required = false) String keyword
    ) {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        Page<TalkSessionVo> pageParam = new Page<>(page, size);
        IPage<TalkSessionVo> sessions = talkService.getSessionList(userId, pageParam, keyword);

        return Result.ok(sessions);
    }

    @Operation(summary = "获取未读消息总数")
    @GetMapping("/unread/count")
    public Result<Integer> getUnreadCount() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        int count = talkService.getTotalUnreadCount(userId);
        return Result.ok(count);
    }

    @Operation(summary = "标记消息为已读")
    @PostMapping("/read/{targetUserId}")
    public Result<Integer> markAsRead(
            @Parameter(description = "目标用户ID", required = true)
            @PathVariable Long targetUserId
    ) {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        int count = talkService.markAsRead(userId, targetUserId);
        return Result.ok(count);
    }

    @Operation(summary = "发送消息")
    @PostMapping("/send")
    public Result<Long> sendMessage(@RequestBody MessageSendRequest request) {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        Long messageId = talkService.sendMessage(userId, request.getReceiverId(), request.getContent());
        return Result.ok(messageId);
    }

    // 添加请求对象类
    public static class MessageSendRequest {
        private Long receiverId;
        private String content;

        // getter和setter方法
        public Long getReceiverId() { return receiverId; }
        public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}