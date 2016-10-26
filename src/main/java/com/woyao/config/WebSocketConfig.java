package com.woyao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.woyao.customer.chat.ChatWebSocketHandler;
import com.woyao.customer.chat.SelfHandshakeInterceptor;

@Configuration
@EnableWebSocket
@Order(3)
public class WebSocketConfig implements WebSocketConfigurer {

	@Value("${websocket.allowedOrigins}")
	private String allowedOrigins;
	
	@Value("${ws.perf.mode}") 
	private boolean perfTestMode;

	@Bean
	public SelfHandshakeInterceptor handshakeInterceptor() {
		SelfHandshakeInterceptor handshake = new SelfHandshakeInterceptor();
		handshake.setCreateSession(false);
		handshake.setPerfTestMode(perfTestMode);
		return handshake;
	}

	@Bean
	public ChatWebSocketHandler chatWebSocketHandler() {
		ChatWebSocketHandler handler = new ChatWebSocketHandler();
		return handler;
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer(
			@Value("${websocket.max.text.buffer.size}") int maxTextMessageBufferSize,
			@Value("${websocket.max.bin.buffer.size}") int maxBinaryMessageBufferSize,
			@Value("${websocket.max.session.idle.timeoutInMillis}") long maxSessionIdleTimeout) {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(maxTextMessageBufferSize);
		container.setMaxBinaryMessageBufferSize(maxBinaryMessageBufferSize);
		container.setMaxSessionIdleTimeout(maxSessionIdleTimeout);
		return container;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this.chatWebSocketHandler(), "/mobile/chat/socket").addInterceptors(this.handshakeInterceptor())
				.setAllowedOrigins(allowedOrigins.split(","));
	}

}
