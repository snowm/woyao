package com.woyao.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

//import com.woyao.admin.controller.StatisticsWebSocketHandler;
import com.snowm.utils.property.EnvUtils;
import com.woyao.customer.chat.ChatWebSocketHandler;
import com.woyao.customer.chat.SelfHandshakeInterceptor;

//@EnableWebSocketMessageBroker
//@Import({ WebSocketSecurityConfig.class })
//public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
@Configuration
@Order(3)
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private Environment env;

	@Bean
	public SelfHandshakeInterceptor handshakeInterceptor() {
		SelfHandshakeInterceptor handshake = new SelfHandshakeInterceptor();
		handshake.setCreateSession(false);
		return handshake;
	}

	@Bean
	public ChatWebSocketHandler chatWebSocketHandler() {
		ChatWebSocketHandler handler = new ChatWebSocketHandler();
		return handler;
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(EnvUtils.getInt(env, "websocket.max.text.buffer.size", 81920));
		container.setMaxBinaryMessageBufferSize(EnvUtils.getInt(env, "websocket.max.bin.buffer.size", 81920));
		container.setMaxSessionIdleTimeout(EnvUtils.getLong(env, "websocket.max.text.buffer.timeoutInMillis", 60000));
		return container;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this.chatWebSocketHandler(), "/mobile/chat/socket").addInterceptors(this.handshakeInterceptor());
	}

}
