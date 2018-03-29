package com.easylinker.proxy.server.app.config.mqttconfig.adapter;

import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;


public class EMqttPahoMessageDrivenChannelAdapter extends MqttPahoMessageDrivenChannelAdapter {


    public EMqttPahoMessageDrivenChannelAdapter(String clientId, MqttPahoClientFactory clientFactory, String... topic) {
        super(clientId, clientFactory, topic);
    }

    @Override
    public synchronized void connectionLost(Throwable cause) {
        System.out.println("连接失败!" + cause.getMessage());
        super.connectionLost(cause);
    }
}
