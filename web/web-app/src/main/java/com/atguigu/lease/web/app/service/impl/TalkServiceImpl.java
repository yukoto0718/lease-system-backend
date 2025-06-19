package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.model.entity.TalkInfo;
import com.atguigu.lease.model.entity.TalkSession;
import com.atguigu.lease.web.app.mapper.TalkInfoMapper;
import com.atguigu.lease.web.app.mapper.TalkSessionMapper;
import com.atguigu.lease.web.app.service.TalkService;
import com.atguigu.lease.web.app.vo.message.TalkMessageVo;
import com.atguigu.lease.web.app.vo.message.TalkSessionVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TalkServiceImpl implements TalkService {

    @Autowired
    private TalkInfoMapper talkInfoMapper;

    @Autowired
    private TalkSessionMapper talkSessionMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long sendMessage(Long senderId, Long receiverId, String content) {

        // 1. 保存消息
        TalkInfo talkInfo = new TalkInfo();
        talkInfo.setSenderId(senderId);
        talkInfo.setReceiverId(receiverId);
        talkInfo.setContent(content);
        talkInfo.setIsRead(0);
        talkInfo.setCreateTime(new Date());
        talkInfoMapper.insert(talkInfo);

        // 2. 更新或创建发送者的会话
        TalkSession senderSession = talkSessionMapper.getOrCreateSession(senderId, receiverId);
        if (senderSession == null) {
            senderSession = new TalkSession();
            senderSession.setUserId(senderId);
            senderSession.setTargetUserId(receiverId);
            senderSession.setLastMessage(content);
            senderSession.setUnreadCount(0);
            senderSession.setLastTime(new Date());
            talkSessionMapper.insert(senderSession);
        } else {
            talkSessionMapper.updateSessionLastMessage(senderId, receiverId, content, false);
        }

        // 3. 更新或创建接收者的会话
        TalkSession receiverSession = talkSessionMapper.getOrCreateSession(receiverId, senderId);
        if (receiverSession == null) {
            receiverSession = new TalkSession();
            receiverSession.setUserId(receiverId);
            receiverSession.setTargetUserId(senderId);
            receiverSession.setLastMessage(content);
            receiverSession.setUnreadCount(1);
            receiverSession.setLastTime(new Date());
            talkSessionMapper.insert(receiverSession);
        } else {
            talkSessionMapper.updateSessionLastMessage(receiverId, senderId, content, true);
        }

        // 4. 更新Redis中的未读消息总数
        String unreadKey = RedisConstant.APP_MESSAGE_UNREAD_COUNT + receiverId;
        Long count = stringRedisTemplate.opsForValue().increment(unreadKey);

        return talkInfo.getId();
    }

    @Override
    public List<TalkMessageVo> getChatHistory(Long userId, Long targetUserId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<TalkMessageVo> messages = talkInfoMapper.selectChatHistory(userId, targetUserId, size, offset);
        return messages;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int markAsRead(Long receiverId, Long senderId) {
        // 检查参数
        if (receiverId == null || senderId == null) {
            log.warn("标记消息为已读时参数无效: receiverId={}, senderId={}", receiverId, senderId);
            return 0;
        }

        log.info("执行标记已读, 接收者ID={}, 发送者ID={}", receiverId, senderId);

        // 1. 标记消息为已读
        int count = talkInfoMapper.markMessagesAsRead(receiverId, senderId);
        log.info("标记了{}条来自{}的消息为已读", count, senderId);

        try {
            if (count > 0) {
                // 2. 重置会话未读计数
                talkSessionMapper.resetUnreadCount(receiverId, senderId);
                log.info("已重置会话{}->{}的未读计数", senderId, receiverId);

                // 3. 关键修改：更新Redis中的未读消息总数 - 直接从数据库获取最新值
                int totalUnreadCount = talkInfoMapper.countTotalUnreadMessages(receiverId);
                String unreadKey = RedisConstant.APP_MESSAGE_UNREAD_COUNT + receiverId;

                if (totalUnreadCount > 0) {
                    stringRedisTemplate.opsForValue().set(unreadKey, String.valueOf(totalUnreadCount));
                    log.info("更新Redis中的未读计数为: {}", totalUnreadCount);
                } else {
                    stringRedisTemplate.delete(unreadKey);
                    log.info("删除Redis中的未读计数键");
                }
            } else {
                log.info("没有需要标记为已读的消息");
            }
        } catch (Exception e) {
            log.error("更新未读计数时发生错误: {}", e.getMessage(), e);
            // 抛出异常使事务回滚
            throw e;
        }

        return count;
    }

    @Override
    public IPage<TalkSessionVo> getSessionList(Long userId, Page<TalkSessionVo> page, String keyword) {
        IPage<TalkSessionVo> result = talkSessionMapper.selectSessionList(page, userId, keyword);
        return result;
    }

    @Override
    public int getTotalUnreadCount(Long userId) {
        int totalUnreadCount = 0;

        try {
            String unreadKey = RedisConstant.APP_MESSAGE_UNREAD_COUNT + userId;
            String countStr = stringRedisTemplate.opsForValue().get(unreadKey);

            // 如果Redis中没有，则从数据库查询
            if (countStr == null) {
                totalUnreadCount = talkInfoMapper.countTotalUnreadMessages(userId);
                log.info("从数据库查询用户{}的总未读数: {}", userId, totalUnreadCount);

                // 更新到Redis
                if (totalUnreadCount > 0) {
                    stringRedisTemplate.opsForValue().set(unreadKey, String.valueOf(totalUnreadCount));
                    log.info("已更新Redis中用户{}的未读计数", userId);
                }
            } else {
                try {
                    totalUnreadCount = Integer.parseInt(countStr);
                    log.info("从Redis获取用户{}的总未读数: {}", userId, totalUnreadCount);

                    // 新增：定期检查Redis和数据库的一致性
                    if (Math.random() < 0.1) { // 10%概率执行一致性检查
                        int dbCount = talkInfoMapper.countTotalUnreadMessages(userId);
                        if (totalUnreadCount != dbCount) {
                            log.warn("用户{}的Redis缓存未读数({})与数据库未读数({})不一致，更新Redis",
                                    userId, totalUnreadCount, dbCount);

                            // 更新Redis为数据库的值
                            if (dbCount > 0) {
                                stringRedisTemplate.opsForValue().set(unreadKey, String.valueOf(dbCount));
                            } else {
                                stringRedisTemplate.delete(unreadKey);
                            }
                            totalUnreadCount = dbCount;
                        }
                    }
                } catch (NumberFormatException e) {
                    log.error("Redis中未读计数格式错误: {}", e.getMessage());
                    // 发生错误时从数据库重新获取
                    totalUnreadCount = talkInfoMapper.countTotalUnreadMessages(userId);

                    // 更新到Redis
                    if (totalUnreadCount > 0) {
                        stringRedisTemplate.opsForValue().set(unreadKey, String.valueOf(totalUnreadCount));
                    } else {
                        stringRedisTemplate.delete(unreadKey);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取用户{}的总未读数时发生错误: {}", userId, e.getMessage());
            // 发生异常时，尝试直接从数据库获取
            try {
                totalUnreadCount = talkInfoMapper.countTotalUnreadMessages(userId);
            } catch (Exception ex) {
                log.error("从数据库获取总未读数时也发生错误: {}", ex.getMessage());
            }
        }

        return totalUnreadCount;
    }
}