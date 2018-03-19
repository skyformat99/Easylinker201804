package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.device.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findTopByDevice(Device device);
}
