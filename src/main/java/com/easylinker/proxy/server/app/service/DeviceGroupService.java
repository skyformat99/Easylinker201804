package com.easylinker.proxy.server.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.dao.DeviceGroupRepository;
import com.easylinker.proxy.server.app.dao.DeviceRepository;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.model.device.DeviceGroup;
import com.easylinker.proxy.server.app.model.user.AppUser;
import com.sun.javafx.geom.transform.BaseTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备组service
 */
@Service("DeviceGroupService")
public class DeviceGroupService {
    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @Autowired
    DeviceRepository deviceRepository;

    public void save(DeviceGroup deviceGroup) {
        deviceGroupRepository.save(deviceGroup);
    }

    public JSONObject getADeviceGroupByName(String name) {
        DeviceGroup deviceGroup = deviceGroupRepository.findTopByGroupName(name);
        if (deviceGroup != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", deviceGroup.getGroupName());
            jsonObject.put("comment", deviceGroup.getComment());
            return jsonObject;
        } else {
            return null;
        }

    }


    public JSONArray getAllByAppUser(AppUser appUser) {
        JSONArray data = new JSONArray();
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAllByAppUser(appUser);
        for (DeviceGroup group : deviceGroupList) {
            JSONObject dataJson = new JSONObject();
            dataJson.put("id", group.getId());
            dataJson.put("user", group.getAppUser().getId());
            dataJson.put("name", group.getGroupName());
            dataJson.put("comment", group.getComment());
            data.add(dataJson);
        }
        return data;
    }

    public DeviceGroup findByGroupName(String name) {
        return deviceGroupRepository.findTopByGroupName(name);
    }


    public void delete(DeviceGroup deviceGroup) {
        deviceGroupRepository.delete(deviceGroup);
    }

    public List<DeviceGroup> findAllDeviceGroupByNameAndAppUser(String groupName, AppUser appUser) {
        return deviceGroupRepository.findAllByGroupNameAndAppUser(groupName, appUser);
    }

    public DeviceGroup findADeviceGroupByName(String groupName) {
        return deviceGroupRepository.findTopByGroupName(groupName);
    }
}
