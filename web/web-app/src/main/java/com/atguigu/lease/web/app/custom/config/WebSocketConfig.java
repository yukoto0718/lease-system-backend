package com.atguigu.lease.web.app.custom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

// 检查是否使用Jakarta包还是Javax包
import jakarta.websocket.server.ServerContainer;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    // Spring会在启动时自动注册WebSocket端点，无需手动配置容器
}