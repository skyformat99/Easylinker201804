package com.sucheon.box.server.app.service;

import com.sucheon.box.server.app.dao.LocationRepository;
import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.device.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LocationService")
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    /**
     * 获取一个设备的位置
     *
     * @param device
     * @return
     */

    public Location getALocationByDevice(Device device) {
        return locationRepository.findTopByDevice(device);

    }

    /**
     * 保存位置
     *
     * @param location
     */

    public void save(Location location) {
        locationRepository.save(location);
    }
}
