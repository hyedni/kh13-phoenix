package com.kh.pheonix.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.kh.pheonix.websocket.ChatbotWebSocketServer;

@EnableWebSocket
@Configuration
public class WebSocketServerConfiguration implements WebSocketConfigurer {

	@Autowired
	private ChatbotWebSocketServer chatbotWebSocketServer;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatbotWebSocketServer, "/ws/chatbot")
                .setAllowedOriginPatterns("*")
                .withSockJS();
	}
	
	
}
