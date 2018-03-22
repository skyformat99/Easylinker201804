package com.sucheon.box.server.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.dao.DeviceRepository;
import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.device.DeviceData;
import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
        List<Device> dataList = deviceRepository.findAllByAppUser(appUser);
        for (Device device : dataList) {
            JSONObject deviceJson = new JSONObject();
            deviceJson.put("name", device.getDeviceName());
            deviceJson.put("barCode", device.getBarCode());
            deviceJson.put("lastActiveDate", device.getLastActiveDate());
            deviceJson.put("describe", device.getDeviceDescribe());
            deviceJson.put("location", device.getLocation().toString());
            data.add(deviceJson);
        }
        return data;
    }

    public JSONArray getAllDevices(Pageable pageable) {
        JSONArray data = new JSONArray();
        List<Device> dataList = deviceRepository.findAll(pageable).getContent();
        for (Device device : dataList) {
            JSONObject deviceJson = new JSONObject();
            deviceJson.put("name", device.getDeviceName());
            deviceJson.put("describe", device.getDeviceDescribe());
            deviceJson.put("barCode", device.getBarCode());
            deviceJson.put("location", device.getLocation().toString());
            deviceJson.put("lastActiveDate", device.getLastActiveDate());
            data.add(deviceJson);
        }
        return data;
    }
}
