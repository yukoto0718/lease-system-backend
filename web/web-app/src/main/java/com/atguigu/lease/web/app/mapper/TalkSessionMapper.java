package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.TalkSession;
import com.atguigu.lease.web.app.vo.message.TalkSessionVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface TalkSessionMapper extends BaseMapper<TalkSession> {

    // 获取用户的会话列表
    IPage<TalkSessionVo> selectSessionList(
            Page<TalkSessionVo> page,
            @Param("userId") Long userId,
            @Param("keyword") String keyword
    );

    // 获取或创建会话
    TalkSession getOrCreateSession(
            @Param("userId") Long userId,
            @Param("targetUserId") Long targetUserId
    );

    // 更新会话的最后消息和未读数
    int updateSessionLastMessage(
            @Param("userId") Long userId,
            @Param("targetUserId") Long targetUserId,
            @Param("lastMessage") String lastMessage,
            @Param("incrementUnread") boolean incrementUnread
    );

    // 重置会话未读数
    int resetUnreadCount(
            @Param("userId") Long userId,
            @Param("targetUserId") Long targetUserId
    );
}

