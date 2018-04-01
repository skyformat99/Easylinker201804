package com.easylinker.proxy.server.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.dao.DeviceGroupRepository;
import com.easylinker.proxy.server.app.dao.DeviceRepository;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.model.device.DeviceGroup;
import com.easylinker.proxy.server.app.model.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备service
 */
@Service("DeviceService")
public class DeviceService {
    @Autowired

    DeviceRepository deviceRepository;


    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    public void save(Device device) {
        deviceRepository.save(device);

    }

    public void delete(Device device) {
        deviceRepository.delete(device);

    }

    public Device findADevice(Long id) {
        return deviceRepository.findTopById(id);
    }


    public JSONArray getAllDevicesByAppUser(AppUser appUser, Pageable pageable) {
        JSONArray data = new JSONArray();
        List<Device> dataList = deviceRepository.findAllByAppUser(appUser, pageable);
        for (Device device : dataList) {
            JSONObject deviceJson = new JSONObject();
            deviceJson.put("name", device.getDeviceName());
            deviceJson.put("barCode", device.getBarCode());
            deviceJson.put("lastActiveDate", device.getLastActiveDate());
            deviceJson.put("location", device.getLocation().toString());
            deviceJson.put("describe", device.getDeviceDescribe());

            data.add(deviceJson);
        }
        return data;
    }


    public JSONArray getAllDevicesByAppUserAndGroup(AppUser appUser, DeviceGroup deviceGroup, Pageable pageable) {
        JSONArray data = new JSONArray();
        List<Device> dataList = deviceRepository.findAllByAppUserAndDeviceGroup(appUser, deviceGroup, pageable);
        for (Device device : dataList) {
            JSONObject deviceJson = new JSONObject();
            deviceJson.put("barCode", device.getBarCode());
            deviceJson.put("name", device.getDeviceName());
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
            if (device.getAppUser() == null) {
                deviceJson.put("isBind", false);
            } else {
                deviceJson.put("isBind", true);
            }
            deviceJson.put("barCode", device.getBarCode());
            deviceJson.put("openId", device.getOpenId());
            deviceJson.put("name", device.getDeviceName());
            deviceJson.put("describe", device.getDeviceDescribe());
            deviceJson.put("location", device.getLocation().getLocationDescribe());
            deviceJson.put("lastActiveDate", device.getLastActiveDate());
            data.add(deviceJson);
        }
        return data;
    }

    public List<Device> findAllDevice() {
        return deviceRepository.findAll();
    }


}
