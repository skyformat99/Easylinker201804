package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findTopById(Long id);

    Device findTopByOpenId(Long openId);

    List<Device> findAllByAppUser(AppUser appUser);


}
