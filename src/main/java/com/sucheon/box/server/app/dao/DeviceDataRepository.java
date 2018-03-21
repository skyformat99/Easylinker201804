package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.device.DeviceData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
    List<DeviceData> findAllByDevice(Device device, Pageable pageable);
}
