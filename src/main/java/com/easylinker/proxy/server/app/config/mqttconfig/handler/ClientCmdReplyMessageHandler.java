package com.easylinker.proxy.server.app.config.mqttconfig.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * IN/CMD
 * 给客户端发送指定的指令，然后返回  在这里处理，暂时打印
 * send -> getInfo
 * reply-> name:rpi,date:2018......
 */
@Component
public class ClientCmdReplyMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println("收到客户端回复命令:" + message);
    }
}
