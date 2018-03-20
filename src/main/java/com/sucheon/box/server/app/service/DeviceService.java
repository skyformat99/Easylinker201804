package com.sucheon.box.server.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.dao.DeviceRepository;
import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.device.DeviceData;
import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备service
 */
@Service("DeviceService")
public class DeviceService {
    @Autowired

    DeviceRepository deviceRepository;

    public void save(Device device) {
        deviceRepository.save(device);

    }

    public void delete(Device device) {
        deviceRepository.delete(device);

    }

    public Device findADevice(Long id) {
        return deviceRepository.findTopById(id);
    }

    public Device getADeviceByOpenId(Long openId) {
        return deviceRepository.findTopByOpenId(openId);
    }

    public JSONArray getAllDevicesByAppUser(AppUser appUser) {
        JSONArray data = new JSONArray();
        List<Device> dataList = deviceRepository.findAll();
        for (Device device : dataList) {
            JSONObject deviceJson = new JSONObject();
            deviceJson.put("name", device.getDeviceName());
            deviceJson.put("describe", device.getDeviceDescribe());
            deviceJson.put("barCode", device.getBarCode());
            deviceJson.put("lastActiveDate", device.getLastActiveDate());
            deviceJson.put("location", device.getLocation().toString());
            data.add(deviceJson);
        }
        return data;
    }
}
