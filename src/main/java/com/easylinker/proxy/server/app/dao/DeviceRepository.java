package com.easylinker.proxy.server.app.dao;

import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.model.device.DeviceGroup;
import com.easylinker.proxy.server.app.model.user.AppUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findTopById(Long id);

    Device findTopByOpenId(Long openId);

    List<Device> findAllByAppUser(AppUser appUser, Pageable pageable);

    List<Device> findAllByAppUserAndDeviceGroup(AppUser appUser, DeviceGroup deviceGroup, Pageable pageable);


}
