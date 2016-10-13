package com.woyao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
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
@EnableWebSocket
@Order(3)
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
		container.setMaxTextMessageBufferSize(EnvUtils.getInt(env, "websocket.max.text.buffer.size", 8192));
		container.setMaxBinaryMessageBufferSize(EnvUtils.getInt(env, "websocket.max.bin.buffer.size", 8192));
		container.setMaxSessionIdleTimeout(EnvUtils.getLong(env, "websocket.max.text.buffer.timeoutInMillis", 60000 * 120));
		return container;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		String property = this.env.getProperty("ws.allowed.origins");
		
		registry.addHandler(this.chatWebSocketHandler(), "/mobile/chat/socket").addInterceptors(this.handshakeInterceptor())
				.setAllowedOrigins(property.split(","));
	}

}
