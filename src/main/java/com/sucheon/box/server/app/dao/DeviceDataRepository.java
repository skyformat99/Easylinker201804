package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.device.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
}
