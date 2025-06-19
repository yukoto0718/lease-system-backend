package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.TalkInfo;
import com.atguigu.lease.web.app.vo.message.TalkMessageVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TalkInfoMapper extends BaseMapper<TalkInfo> {

    // 获取两个用户之间的聊天记录
    List<TalkMessageVo> selectChatHistory(
            @Param("userId") Long userId,
            @Param("targetUserId") Long targetUserId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    // 将用户接收的消息标记为已读
    int markMessagesAsRead(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

    /**
     * 统计指定发送者和接收者之间的未读消息数
     */
    int countUnreadMessages(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    /**
     * 统计指定接收者的所有未读消息总数
     */
    int countTotalUnreadMessages(@Param("receiverId") Long receiverId);
}
