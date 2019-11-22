package com.springboot.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Description:
 * @Package: com.springboot.websocket
 * @Author: denglt
 * @Date: 2019-11-08 14:33
 * @Copyright: 版权归 HSYUNTAI 所有
 */
public class MyTextWebSocketHandler extends TextWebSocketHandler {


    private static final Logger logger = LoggerFactory.getLogger(MyTextWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("connect from {} ", session.getRemoteAddress());
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        logger.info("receive {} from {} ", message.getPayload(), session.getRemoteAddress());
        // session.getAttributes(); 在HandshakeInterceptor.beforeHandshake 中设置， @see HttpSessionHandshakeInterceptor
        String payload = message.getPayload();
        JSONObject jsonObject = JSON.parseObject(payload);
        session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("close from {} ", session.getRemoteAddress());
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Handle TransportError : ", exception);
        //super.handleTransportError(session, exception);
        session.close();
    }
}
