package com.sucheon.box.server.app.config.mqttconfig.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * 实时消息广播监听器，在这里仅仅是测试作用
 * 后期会关闭监控，仅仅留给websocket使用
 *
 */
@Component
public class RealTimeMessageHandler implements MessageHandler {
    Logger logger = LoggerFactory.getLogger(RealTimeMessageHandler.class);

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        logger.info("实时消息:" + message.getPayload());

    }
}
