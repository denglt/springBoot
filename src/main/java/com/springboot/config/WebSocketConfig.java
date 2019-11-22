package com.springboot.config;

import com.springboot.websocket.MyTextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2019-11-08 13:42
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer { // 以后可以搞个STOMP的实现

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myHandler(), "/myWebSocketHandler")
                .addInterceptors(new MyHandshakeInterceptor(), new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler()) // new MyHandshakeHandler
                .setAllowedOrigins("*"); // 跨域
        //.withSockJS();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    @Bean
    public WebSocketHandler myHandler() {
        System.out.println("我只执行一次");
        return new MyTextWebSocketHandler();
    }
}

class MyHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MyHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("Before Handshake");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        logger.info("After Handshake");
    }
}

class MyHandshakeHandler extends AbstractHandshakeHandler {

}