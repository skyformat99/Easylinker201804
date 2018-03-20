package com.sucheon.box.server.app.config.mqttconfig.handler;

import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.model.device.AudioData;
import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.service.AudioDataService;
import com.sucheon.box.server.app.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 数据消息接收器
 * Created by wwhai on 2018/3/14.
 * [payload=HelloWorld, headers={mqtt_retained=false, mqtt_qos=0, id=b8ed902e-0664-08ac-b013-0a48e612c057, mqtt_topic=IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/1521508320898, mqtt_duplicate=false, timestamp=1521533990697}]
 */

/**
 * {
 * 　　"hostName":"DESKTOP-P4US2OO",
 * 　　"UUID":"8CEC4B5D447F",
 * 　　"sshPort":7034,
 * 　　"eth0MAC":"8CEC4B5D447F",
 * 　　"wlan0MAC":"8CEC4B5D447F",
 * 　　"user":"",
 * 　　"city":"",
 * 　　"factory":"",
 * 　　"workshop":"",
 * 　　"line":"",
 * 　　"device":"",
 * 　　"tags":""
 * }
 */
@Component
public class InMessageHandler implements MessageHandler {
    Logger logger = LoggerFactory.getLogger(ClientOnAndOfflineWillMessageHandler.class);

    @Autowired
    AudioDataService audioDataService;
    @Autowired
    DeviceService deviceService;

    @Override
    //mqtt_topic=IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/1521508320898
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = message.getHeaders().get("mqtt_topic").toString();

        try {
            if (topic.startsWith("IN/DEVICE/")) {
                Long openId = Long.parseLong(topic.split("/")[4]);
                JSONObject dataJson = JSONObject.parseObject(message.getPayload().toString());
                Device device = deviceService.findADevice(openId);
                if (device != null) {
                    Date date = new Date();
                    Double std = dataJson.getDoubleValue("std");
                    Double meanHf = dataJson.getDoubleValue("meanHf");
                    Double meanLf = dataJson.getDoubleValue("meanLf");
                    String feature1 = dataJson.getString("feature1");
                    String feature2 = dataJson.getString("feature2");
                    String feature3 = dataJson.getString("feature3");
                    String feature4 = dataJson.getString("feature4");
                    String bandSpectrum = dataJson.getString("bandSpectrum");
                    String peakFrequency = dataJson.getString("peakFrequency");
                    String peakPowers = dataJson.getString("peakPowers");
                    AudioData audioData = new AudioData(date, std, meanHf, meanLf, feature1,
                            feature2, feature3, feature4,
                            bandSpectrum, peakFrequency, peakPowers, device
                    );
                    audioDataService.save(audioData);
                    logger.info("数据保存成功!");
                }


            }
        } catch (Exception e) {
            //提交的格式不正确
            //只接受 from:IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/1521508320898
            logger.error("数据格式出错!");

        }

    }

    public static void main(String[] args) {
        Double std = 1.1;
        Double meanHf = 2.1;
        Double meanLf = 3.1;
        String feature1 = "feature1";
        String feature2 = "feature2";
        String feature3 = "feature3";
        String feature4 = "feature4";
        String bandSpectrum = "bandSpectrum";
        String peakFrequency = "peakFrequency";
        String peakPowers = "peakPowers";
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("std",std);
        jsonObject.put("meanLf",meanLf);
        jsonObject.put("meanHf",meanHf);
        jsonObject.put("feature1",feature1);
        jsonObject.put("feature2",feature2);
        jsonObject.put("feature3",feature3);
        jsonObject.put("feature4",feature4);
        jsonObject.put("bandSpectrum",bandSpectrum);
        jsonObject.put("peakFrequency",peakFrequency);
        jsonObject.put("peakPowers",peakPowers);
        System.out.println(jsonObject.toString());

    }
}
