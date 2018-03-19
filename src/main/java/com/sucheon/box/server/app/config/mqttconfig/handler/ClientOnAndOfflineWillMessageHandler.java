package com.sucheon.box.server.app.config.mqttconfig.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * Created by wwhai on 2018/3/14.
 */
public class ClientOnAndOfflineWillMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

    }
}
