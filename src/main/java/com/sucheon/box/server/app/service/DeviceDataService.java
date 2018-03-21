package com.sucheon.box.server.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.dao.DeviceDataRepository;
import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.device.DeviceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DeviceDataService")
public class DeviceDataService {
    @Autowired
    DeviceDataRepository deviceDataRepository;

    public void save(DeviceData deviceData) {
        deviceDataRepository.save(deviceData);
    }

    public JSONArray getAllDeviceDataByDevice(Device device, Pageable pageable) {
        JSONArray data = new JSONArray();
        List<DeviceData> dataList = deviceDataRepository.findAllByDevice(device,pageable);
        for (DeviceData deviceData : dataList) {
            JSONObject dataJson = new JSONObject();
            dataJson.put("data", JSONObject.parse(deviceData.getData()));
            dataJson.put("create_time", deviceData.getCreateTime());
            dataJson.put("id", deviceData.getId());
            data.add(dataJson);
        }
        return data;
    }
}
