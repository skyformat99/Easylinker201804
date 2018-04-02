package com.easylinker.proxy.server.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.constants.result.ReturnResult;
import com.easylinker.proxy.server.app.model.device.DeviceData;
import com.easylinker.proxy.server.app.service.AppUserService;
import com.easylinker.proxy.server.app.service.DeviceDataService;
import com.easylinker.proxy.server.app.service.DeviceGroupService;
import com.easylinker.proxy.server.app.service.DeviceService;
import com.easylinker.proxy.server.app.utils.HttpTool;
import org.apache.poi.util.Removal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 给HTTP使用
 */
@RestController
@RequestMapping("/api/v1")
public class HTTPApiController {

    @Value("${emq.api.host}")
    String apiHost;
    /**
     * 通过HTTP接口发送数据
     * {
     * "topic"    : "test",
     * "payload"  : "hello",
     * "qos"      : 1,
     * "retain"   : false,
     * "client_id": "C_1492145414740"
     * }
     * curl -v --basic -u <user>:<passwd> -k http://localhost:8080/api/v2/nodes/emq@127.0.0.1/clients
     *
     * @return
     */
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DeviceService deviceService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    DeviceGroupService deviceGroupService;
    @Autowired
    HttpTool httpTool;
    @Autowired
    DeviceDataService deviceDataService;

    /**
     * 注意：默认只接受JSON格式的CMD 其他格式直接返回结果
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/sendCmdToDevice", method = RequestMethod.POST)
    public JSONObject sendCmdToDevice(@RequestBody JSONObject body) {
        JSONObject cmd = new JSONObject();
        JSONObject payload = body.getJSONObject("payload");
        Long deviceId = body.getLongValue("deviceId");

        if (payload == null || deviceId == null) {
            return ReturnResult.returnTipMessage(0, "参数不全!");
        } else {
            Device device = deviceService.findADevice(deviceId);
            if (device != null && device.getAppUser() != null) {
                cmd.put("topic", device.getTopic().replace("IN","OUT"));
                try {
                    cmd.put("payload", payload.toJSONString());//这里许哟啊注意：payload必须是String类型的
                } catch (Exception e) {
                    return ReturnResult.returnTipMessage(0, "消息格式必须是JSON!");

                }
                cmd.put("qos", 1);
                cmd.put("retain", false);
                cmd.put("client_id", "SERVER_PROXY");
                try {
                    httpTool.postWithAuthorization(apiHost + "mqtt/publish", cmd);
                } catch (Exception e) {
                    return ReturnResult.returnTipMessage(0, "发送失败!");
                }
                return ReturnResult.returnTipMessage(1, "发送成功!");
            } else {
                return ReturnResult.returnTipMessage(0, "设备不存在或者没有绑定!");
            }

        }
    }

    /**
     * 设备端通过HTTP POST数据进来
     *
     * @return
     */
    @RequestMapping(value = "/postDataWithHttp")
    public JSONObject receivePostFromClientWithHttp(@RequestBody JSONObject body) {
        Long deviceId = body.getLongValue("deviceId");
        JSONObject data = body.getJSONObject("data");
        if (data == null || deviceId == null) {
            return ReturnResult.returnTipMessage(0, "参数不全!");
        } else {
            Device device = deviceService.findADevice(deviceId);
            if (device != null) {
                if (device.getAppUser() != null) {
                    DeviceData deviceData = new DeviceData();
                    deviceData.setDevice(device);
                    deviceData.setData(data.toJSONString());
                    deviceDataService.save(deviceData);
                    return ReturnResult.returnTipMessage(0, "数据提交成功!");
                } else {
                    return ReturnResult.returnTipMessage(0, "设备没有绑定!");
                }


            } else {
                return ReturnResult.returnTipMessage(0, "设备不存在!");
            }
        }
    }
}
