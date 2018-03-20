package com.sucheon.box.server.app.config.mqttconfig.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * 服务器向外部发送消息
 * Created by wwhai on 2018/3/14.
 */
@Component
public class OutMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println("服务器给客户端发送了消息:" + message.getPayload());

    }
}
