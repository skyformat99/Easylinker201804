package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findTopById(Long id);
}
