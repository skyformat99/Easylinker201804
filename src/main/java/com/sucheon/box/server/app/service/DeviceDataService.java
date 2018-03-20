package com.sucheon.box.server.app.service;

import com.sucheon.box.server.app.dao.DeviceDataRepository;
import com.sucheon.box.server.app.model.device.DeviceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DeviceDataService")
public class DeviceDataService {
    @Autowired
    DeviceDataRepository deviceDataRepository;

    public void save(DeviceData deviceData) {
        deviceDataRepository.save(deviceData);
    }
}
