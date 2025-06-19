package com.atguigu.lease.web.app.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.atguigu.lease.web.app.service.TalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ServerEndpoint("/websocket/chat/{userId}")
public class WebSocketServer {

    // 用于存储在线连接，key为用户ID
    public static final Map<Long, Session> ONLINE_SESSIONS = new ConcurrentHashMap<>();

    // 消息去重集合 - 使用LinkedHashSet保持插入顺序，方便移除最旧的元素
    private static final Set<Long> RECENT_MESSAGE_IDS = new LinkedHashSet<>();
    private static final int MAX_RECENT_MESSAGES = 500; // 增加缓存大小
    private static final Object MESSAGE_ID_LOCK = new Object(); // 专用锁对象，避免类锁

    // JSON处理
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 服务注入 - 使用实例持有器模式
    private static TalkService talkService;

    // 实例变量 - 保存当前会话信息
    private Session session;
    private Long userId;

    /**
     * 改进的服务注入方法
     */
    @Autowired
    public void setTalkService(TalkService service) {
        WebSocketServer.talkService = service;
    }

    /**
     * 连接建立成功调用
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        // 保存到实例变量
        this.session = session;
        this.userId = userId;

        // 设置会话超时时间 - 减少到30分钟
        try {
            session.setMaxIdleTimeout(1800000); // 30分钟
        } catch (Exception e) {
            log.warn("设置会话超时时间失败: {}", e.getMessage());
        }

        // 设置消息缓冲区大小
        try {
            session.setMaxTextMessageBufferSize(65535);
            session.setMaxBinaryMessageBufferSize(65535);
        } catch (Exception e) {
            log.warn("设置消息缓冲区大小失败: {}", e.getMessage());
        }

        // 处理同一用户的旧连接
        Session oldSession = ONLINE_SESSIONS.get(userId);
        if (oldSession != null && oldSession.isOpen()) {
            try {
                log.debug("【调试】用户{}有旧的连接，尝试关闭", userId);
                oldSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "新连接替代"));
            } catch (IOException e) {
                log.warn("关闭用户{}的旧连接失败: {}", userId, e.getMessage());
            }
        }

        // 记录新连接
        ONLINE_SESSIONS.put(userId, session);
        log.info("【调试】用户{}建立连接成功，当前在线人数为：{}", userId, ONLINE_SESSIONS.size());
        log.info("【调试】当前在线用户列表: {}", ONLINE_SESSIONS.keySet());

        // 发送连接成功通知
        try {
            ObjectNode response = objectMapper.createObjectNode();
            response.put("type", "system");
            response.put("content", "连接已建立");
            response.put("timestamp", System.currentTimeMillis());
            session.getBasicRemote().sendText(response.toString());
        } catch (IOException e) {
            log.error("发送连接成功通知失败: {}", e.getMessage());
        }
    }

    /**
     * 连接关闭调用 - 改进的会话移除逻辑
     */
    @OnClose
    public void onClose(CloseReason reason) {
        if (this.userId != null && this.session != null) {
            // 只移除当前实例的会话，避免错误地移除其他新连接
            Session currentSession = ONLINE_SESSIONS.get(this.userId);
            if (currentSession != null && currentSession.getId().equals(this.session.getId())) {
                ONLINE_SESSIONS.remove(this.userId);
                log.info("【调试】用户{}的WebSocket连接已关闭，原因: {}，当前在线人数: {}",
                        this.userId, reason != null ? reason.getReasonPhrase() : "未知",
                        ONLINE_SESSIONS.size());
                log.info("【调试】当前在线用户: {}", ONLINE_SESSIONS.keySet());
            } else {
                log.info("【调试】用户{}的旧连接关闭，不需要从在线列表移除", this.userId);
            }
        }
    }

    /**
     * 收到客户端消息后调用
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if (this.userId == null) {
            log.warn("收到未知用户的消息");
            return;
        }

        try {
            // 解析消息
            JsonNode jsonNode = objectMapper.readTree(message);
            String type = jsonNode.get("type").asText();

            switch (type) {
                case "ping":
                    // 心跳消息处理
                    handlePingMessage(session);
                    break;
                case "chat":
                    // 聊天消息处理
                    handleChatMessage(jsonNode);
                    break;
                case "read":
                    // 已读消息处理
                    handleReadMessage(jsonNode);
                    break;
                case "status":
                    // 状态消息处理
                    handleStatusMessage(jsonNode);
                    break;
                case "debug":
                    // 调试消息处理
                    handleDebugMessage(jsonNode);
                    break;
                default:
                    log.warn("收到未知类型的消息: {}", type);
                    // 发送错误响应
                    sendErrorResponse(session, "未知的消息类型: " + type, "INVALID_TYPE");
            }
        } catch (IllegalArgumentException e) {
            log.error("消息格式无效: {}", e.getMessage());
            sendErrorResponse(session, "消息格式不正确", "VALIDATION_ERROR");
        } catch (DataAccessException e) {
            log.error("数据库操作失败: {}", e.getMessage(), e);
            sendErrorResponse(session, "服务器处理消息时遇到问题", "DATABASE_ERROR");
        } catch (Exception e) {
            log.error("处理用户{}的WebSocket消息时发生异常: {}", this.userId, e.getMessage(), e);
            sendErrorResponse(session, "消息处理失败", "UNKNOWN_ERROR");
        }
    }

    /**
     * 处理调试消息
     */
    private void handleDebugMessage(JsonNode jsonNode) {
        try {
            // 返回在线用户列表和会话状态等调试信息
            ObjectNode response = objectMapper.createObjectNode();
            response.put("type", "debug_response");
            response.put("timestamp", System.currentTimeMillis());
            response.put("sender", this.userId);
            response.put("online_count", ONLINE_SESSIONS.size());

            // 添加在线用户列表
            StringBuilder users = new StringBuilder();
            for (Long id : ONLINE_SESSIONS.keySet()) {
                users.append(id).append(",");
            }
            response.put("online_users", users.toString());

            // 会话状态
            response.put("session_open", this.session != null && this.session.isOpen());

            // 消息去重集合大小
            synchronized (MESSAGE_ID_LOCK) {
                response.put("message_cache_size", RECENT_MESSAGE_IDS.size());
            }

            session.getBasicRemote().sendText(response.toString());
            log.info("【调试】已发送调试信息响应");
        } catch (Exception e) {
            log.error("【调试】发送调试响应失败", e);
        }
    }

    /**
     * 处理状态消息
     */
    private void handleStatusMessage(JsonNode jsonNode) {
        try {
            String status = jsonNode.get("status").asText();
            log.info("【调试】用户{}的状态更新为: {}", this.userId, status);

            // 这里可以添加特定的状态处理逻辑，比如更新用户状态到数据库
            // 目前仅记录日志，不执行特定操作
        } catch (Exception e) {
            log.error("处理状态消息失败: {}", e.getMessage());
        }
    }

    /**
     * 处理心跳消息
     */
    private void handlePingMessage(Session session) {
        try {
            // 获取当前用户的总未读消息数 - 每次心跳都从服务获取最新数据
            int currentUserTotalUnread = 0;
            if (this.userId != null) {
                // 直接从服务获取最新未读数，确保准确性
                currentUserTotalUnread = talkService.getTotalUnreadCount(this.userId);
                log.debug("【心跳】用户{}的总未读消息数: {}", this.userId, currentUserTotalUnread);
            }

            ObjectNode response = objectMapper.createObjectNode();
            response.put("type", "pong");
            response.put("timestamp", System.currentTimeMillis());
            response.put("totalUnreadCount", currentUserTotalUnread); // 在心跳响应中添加总未读数
            session.getBasicRemote().sendText(response.toString());
            log.debug("已响应用户{}的心跳消息", this.userId);
        } catch (IOException e) {
            log.error("向用户{}发送心跳响应失败: {}", this.userId, e.getMessage());
        }
    }

    /**
     * 改进的错误响应方法，添加错误类型
     */
    private void sendErrorResponse(Session session, String errorMessage, String errorType) {
        try {
            ObjectNode response = objectMapper.createObjectNode();
            response.put("type", "error");
            response.put("message", errorMessage);
            response.put("errorType", errorType);
            response.put("timestamp", System.currentTimeMillis());
            session.getBasicRemote().sendText(response.toString());
        } catch (IOException e) {
            log.error("向用户{}发送错误响应失败: {}", this.userId, e.getMessage());
        }
    }


    /**
     * 处理聊天消息
     */
    private void handleChatMessage(JsonNode jsonNode) {
        try {
            // 解析消息内容
            Long senderId = this.userId;
            Long receiverId = jsonNode.get("receiverId").asLong();
            String content = jsonNode.get("content").asText();

            log.info("【调试】收到聊天消息: 发送者={}, 接收者={}, 内容={}", senderId, receiverId, content);

            // 检查接收者是否在线
            Session receiverSession = ONLINE_SESSIONS.get(receiverId);
            log.info("【调试】接收者{}是否在线: {}", receiverId, receiverSession != null && receiverSession.isOpen());

            // 客户端消息ID检查(去重) - 使用专用锁对象
            Long clientMsgId = null;
            if (jsonNode.has("clientMsgId")) {
                clientMsgId = jsonNode.get("clientMsgId").asLong();
                log.info("【调试】消息携带客户端ID: {}", clientMsgId);

                // 检查是否是重复消息
                boolean isDuplicate = false;
                synchronized (MESSAGE_ID_LOCK) {
                    if (RECENT_MESSAGE_IDS.contains(clientMsgId)) {
                        isDuplicate = true;
                    } else {
                        // 添加到最近消息ID集合
                        RECENT_MESSAGE_IDS.add(clientMsgId);

                        // 如果集合过大，移除最早的ID (LinkedHashSet保证顺序)
                        if (RECENT_MESSAGE_IDS.size() > MAX_RECENT_MESSAGES) {
                            Long oldestId = RECENT_MESSAGE_IDS.iterator().next();
                            RECENT_MESSAGE_IDS.remove(oldestId);
                            log.debug("消息缓存超过阈值，移除最旧的消息ID: {}", oldestId);
                        }
                    }
                }

                if (isDuplicate) {
                    log.info("检测到重复消息ID: {}，忽略处理", clientMsgId);
                    return;
                }
            }

            // 打印消息发送和接收者信息
            log.info("保存聊天消息: 发送者={}, 接收者={}, 内容={}", senderId, receiverId, content);

            // 保存消息到数据库
            Long messageId = talkService.sendMessage(senderId, receiverId, content);
            log.info("【调试】消息已保存到数据库，ID={}", messageId);

            // 获取接收者的总未读消息数
            int receiverTotalUnread = talkService.getTotalUnreadCount(receiverId);
            log.info("【调试】接收者{}的总未读消息数: {}", receiverId, receiverTotalUnread);

            // 获取发送者的总未读消息数(新增)
            int senderTotalUnread = talkService.getTotalUnreadCount(senderId);
            log.info("【调试】发送者{}的总未读消息数: {}", senderId, senderTotalUnread);

            long timestamp = System.currentTimeMillis();

            // 构建返回给发送者的消息（包含status字段和发送者总未读数）
            ObjectNode senderResponse = objectMapper.createObjectNode();
            senderResponse.put("type", "chat");
            senderResponse.put("messageId", messageId);
            senderResponse.put("senderId", senderId);
            senderResponse.put("receiverId", receiverId);
            senderResponse.put("content", content);
            senderResponse.put("timestamp", timestamp);
            senderResponse.put("status", "sent");
            senderResponse.put("totalUnreadCount", senderTotalUnread); // 新增：发送者的总未读数
            if (clientMsgId != null) {
                senderResponse.put("clientMsgId", clientMsgId);
            }

            // 发送给发送者（当前用户）
            sendMessageToUser(senderId, senderResponse.toString());

            // 构建发送给接收者的消息
            ObjectNode receiverResponse = objectMapper.createObjectNode();
            receiverResponse.put("type", "chat");
            receiverResponse.put("messageId", messageId);
            receiverResponse.put("senderId", senderId);
            receiverResponse.put("receiverId", receiverId);
            receiverResponse.put("content", content);
            receiverResponse.put("timestamp", timestamp);
            receiverResponse.put("totalUnreadCount", receiverTotalUnread); // 添加总未读数

            // 发送给接收者
            sendMessageToUser(receiverId, receiverResponse.toString());

        } catch (Exception e) {
            log.error("处理聊天消息失败: {}", e.getMessage(), e);
            sendErrorResponse(this.session, "处理聊天消息失败", "CHAT_ERROR");
        }
    }

    /**
     * 处理已读消息通知
     */
    private void handleReadMessage(JsonNode jsonNode) {
        try {
            // 获取目标用户ID
            Long targetUserId = jsonNode.get("targetUserId").asLong();

            log.info("【已读通知】收到已读消息通知: 用户{}标记来自用户{}的消息为已读", this.userId, targetUserId);

            // 标记消息为已读 - 当前用户(this.userId)是接收者，targetUserId是发送者
            int count = talkService.markAsRead(this.userId, targetUserId);
            log.info("【已读通知】用户{}标记{}条来自用户{}的消息为已读", this.userId, count, targetUserId);

            // 获取当前用户更新后的总未读消息数 - 关键修改
            int currentUserTotalUnread = talkService.getTotalUnreadCount(this.userId);
            log.info("【已读通知】用户{}的总未读消息数更新为: {}", this.userId, currentUserTotalUnread);

            // 目标用户的总未读消息数
            int targetUserTotalUnread = talkService.getTotalUnreadCount(targetUserId);
            log.info("【已读通知】目标用户{}的总未读消息数: {}", targetUserId, targetUserTotalUnread);

            // 1. 发送已读通知给对方
            ObjectNode readNotification = objectMapper.createObjectNode();
            readNotification.put("type", "read");
            readNotification.put("fromUserId", this.userId);
            readNotification.put("targetUserId", targetUserId);
            readNotification.put("count", count);
            readNotification.put("timestamp", System.currentTimeMillis());
            readNotification.put("totalUnreadCount", targetUserTotalUnread);

            // 发送给目标用户
            sendMessageToUser(targetUserId, readNotification.toString());

            // 2. 发送已读确认给当前用户 - 包含最新的总未读数
            ObjectNode readConfirmation = objectMapper.createObjectNode();
            readConfirmation.put("type", "read_confirm");
            readConfirmation.put("targetUserId", targetUserId);
            readConfirmation.put("count", count);
            readConfirmation.put("timestamp", System.currentTimeMillis());
            readConfirmation.put("success", true);
            readConfirmation.put("totalUnreadCount", currentUserTotalUnread); // 最新的总未读数

            sendMessageToUser(this.userId, readConfirmation.toString());

        } catch (Exception e) {
            log.error("处理已读消息失败: {}", e.getMessage(), e);
            sendErrorResponse(this.session, "处理已读消息失败", "READ_ERROR");
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket连接发生错误: {}", error.getMessage(), error);
        // 连接出错时，从在线列表中移除
        if (this.userId != null) {
            // 确保只移除当前会话
            Session currentSession = ONLINE_SESSIONS.get(this.userId);
            if (currentSession != null && currentSession.getId().equals(session.getId())) {
                ONLINE_SESSIONS.remove(this.userId);
                log.info("【调试】因错误移除用户{}的连接", this.userId);
            }
        }
    }

    /**
     * 发送消息给指定用户
     */
    private void sendMessageToUser(Long userId, String message) {
        Session targetSession = ONLINE_SESSIONS.get(userId);
        log.info("【调试】尝试向用户{}发送消息，用户是否在线: {}", userId, targetSession != null && targetSession.isOpen());

        if (targetSession != null && targetSession.isOpen()) {
            try {
                targetSession.getBasicRemote().sendText(message);
                log.info("【调试】成功发送消息给用户{}", userId);
            } catch (IOException e) {
                log.error("【调试】发送消息给用户{}失败: {}", userId, e.getMessage());
                // 发生错误时，移除无效连接
                ONLINE_SESSIONS.remove(userId);
            }
        } else {
            log.info("【调试】用户{}不在线或连接已关闭，无法发送消息", userId);
            // 确保移除无效连接
            ONLINE_SESSIONS.remove(userId);
        }
    }

    /**
     * 广播消息给所有在线用户
     * 用于系统通知等功能
     */
    public static void broadcastMessage(String message) {
        log.info("【调试】广播消息给所有用户: {}", message);
        int successCount = 0;

        for (Map.Entry<Long, Session> entry : ONLINE_SESSIONS.entrySet()) {
            Long userId = entry.getKey();
            Session session = entry.getValue();

            if (session != null && session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                    successCount++;
                    log.debug("广播消息给用户{}成功", userId);
                } catch (IOException e) {
                    log.error("广播消息给用户{}失败: {}", userId, e.getMessage());
                    // 连接已失效，从在线列表移除
                    ONLINE_SESSIONS.remove(userId);
                }
            } else {
                // 连接已关闭，从在线列表移除
                log.info("【调试】移除离线用户{}", userId);
                ONLINE_SESSIONS.remove(userId);
            }
        }

        log.info("【调试】广播消息成功发送给{}个用户", successCount);
    }

    /**
     * 获取在线用户数
     */
    public static int getOnlineCount() {
        return ONLINE_SESSIONS.size();
    }

    /**
     * 判断用户是否在线
     */
    public static boolean isUserOnline(Long userId) {
        Session session = ONLINE_SESSIONS.get(userId);
        return session != null && session.isOpen();
    }
}