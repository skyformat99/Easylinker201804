package com.easylinker.proxy.server.app.config.mqttconfig.handler;

import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.config.mqttconfig.MqttMessageSender;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.model.device.DeviceData;
import com.easylinker.proxy.server.app.service.DeviceDataService;
import com.easylinker.proxy.server.app.service.DeviceService;
import com.easylinker.proxy.server.app.utils.HttpTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * 从客户端进来的新消息
 */
@Component
public class InMessageHandler implements MessageHandler {
    Logger logger = LoggerFactory.getLogger(ClientOnAndOfflineWillMessageHandler.class);

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceDataService deviceDataService;
    @Autowired
    MqttMessageSender mqttMessageSender;
    @Autowired
    HttpTool httpTool;
    @Value("${emq.api.host}")
    String apiHost;

    @Override
    //mqtt_topic=IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/1521508320898
    //这里必须这么写，以内ACL控制了Topic
    public void handleMessage(Message<?> message) throws MessagingException {


        String topic = message.getHeaders().get("mqtt_topic").toString();
        try {
            if (topic.startsWith("IN/DEVICE/")) {
                Long openId = Long.parseLong(topic.split("/")[4]);
                JSONObject dataJson = JSONObject.parseObject(message.getPayload().toString());
                Device device = deviceService.findADevice(openId);
                if (device != null) {
                    mqttMessageSender.sendRealTimePureMessage("数据传输中!");
                    if (device.getAppUser() == null) {
                        logger.info("默认分组的设备，数据不记录!");
                    } else {
                        /**
                         * 这里AudioData是自定义数据格式
                         * 数据全部进 DeviceData
                         */
                        DeviceData deviceData = new DeviceData();
                        deviceData.setDevice(device);
                        deviceData.setData(dataJson.toString());
                        deviceDataService.save(deviceData);
                        logger.info("数据保存成功!");
                        mqttMessageSender.sendRealTimeDeviceMessage(device, "设备:[" + device.getDeviceName() + "]数据传输成功!");

                    }
                } else {
                    logger.info("设备不存在!");
                }


            }
        } catch (Exception e) {
            //提交的格式不正确
            //只接受 from:IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/1521508320898
            logger.error("数据格式出错!");

        }

    }

}
