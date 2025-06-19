package com.atguigu.lease.web.app.service;

import com.atguigu.lease.web.app.vo.message.TalkMessageVo;
import com.atguigu.lease.web.app.vo.message.TalkSessionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface TalkService {

    /**
     * 发送消息
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @param content 消息内容
     * @return 消息ID
     */
    Long sendMessage(Long senderId, Long receiverId, String content);

    /**
     * 获取聊天历史记录
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 聊天记录列表
     */
    List<TalkMessageVo> getChatHistory(Long userId, Long targetUserId, Integer page, Integer size);

    /**
     * 标记与指定用户的聊天为已读
     * @param receiverId 接收者ID
     * @param senderId 发送者ID
     * @return 已读消息数量
     */
    int markAsRead(Long receiverId, Long senderId);

    /**
     * 获取会话列表
     * @param userId 用户ID
     * @param page 分页参数
     * @param keyword 搜索关键词
     * @return 会话列表
     */
    IPage<TalkSessionVo> getSessionList(Long userId, Page<TalkSessionVo> page, String keyword);

    /**
     * 获取未读消息总数
     * @param userId 用户ID
     * @return 未读消息总数
     */
    int getTotalUnreadCount(Long userId);
}

