package com.sucheon.box.server.app.constants.mqtt;

public enum MqttTopic {
    SERVER_IN_TOPIC("IN/DEVICE/+/#","服务器监听所有分组，所有消息"),
    DEVICE_IN_TOPIC("OUT/DEVICE/","");


    private String topic;
    private String comment;


    MqttTopic(String topic, String comment) {

        this.comment = comment;
        this.topic = topic;
    }
}
