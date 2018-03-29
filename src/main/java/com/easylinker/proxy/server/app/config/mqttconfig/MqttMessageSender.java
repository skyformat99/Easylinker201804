package com.easylinker.proxy.server.app.config.mqttconfig;

import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.utils.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息辅助发送
 */
@Component
public class MqttMessageSender {
    @Autowired
    HttpTool httpTool;
    @Value("${emq.api.host}")
    String apiHost;

    /**
     * 发送实时消息
     *
     * @param device
     * @param message
     * @throws Exception
     */

    public void sendRealTimeDeviceMessage(Device device, String message) throws Exception {
        JSONObject messageJson = new JSONObject();
        messageJson.put("topic", "OUT/REAL_TIME");
        messageJson.put("payload", message);//这里注意：payload必须是String类型的
        messageJson.put("qos", 1);
        messageJson.put("retain", false);
        messageJson.put("client_id", "SERVER_PROXY");
        httpTool.postWithAuthorization(apiHost + "mqtt/publish", messageJson);
    }

    /**
     *
     * @param message
     * @throws Exception
     */
    public void sendRealTimePureMessage(String message) throws Exception {
        JSONObject messageJson = new JSONObject();
        messageJson.put("topic", "OUT/REAL_TIME");
        messageJson.put("payload", message);
        messageJson.put("retain", false);
        messageJson.put("qos", 1);
        messageJson.put("client_id", "SERVER_PROXY");
        httpTool.postWithAuthorization(apiHost + "mqtt/publish", messageJson);
    }
}
