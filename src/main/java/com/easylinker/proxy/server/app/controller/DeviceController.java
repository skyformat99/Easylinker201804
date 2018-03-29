package com.easylinker.proxy.server.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.constants.result.ReturnResult;
import com.easylinker.proxy.server.app.service.DeviceDataService;
import com.easylinker.proxy.server.app.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备操作业务逻辑
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    DeviceDataService deviceDataService;



    /**
     * 获取设备产生的数据
     *
     * @return
     */
    @RequestMapping(value = "/getDeviceData/{deviceId}/{page}/{size}", method = RequestMethod.GET)
    public JSONObject getDeviceData(@PathVariable Long deviceId, @PathVariable int page, @PathVariable int size) {
        Device device = deviceService.findADevice(deviceId);
        if (device != null) {
            return ReturnResult.returnDataMessage(1, "查询成功!", deviceDataService.getAllDeviceDataByDevice(device, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))));

        } else {
            return ReturnResult.returnTipMessage(0, "设备不存在!");
        }

    }

}
