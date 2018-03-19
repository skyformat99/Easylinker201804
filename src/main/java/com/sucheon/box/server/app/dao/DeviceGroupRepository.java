package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.device.DeviceGroup;
import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {
    List<DeviceGroup> findAllByAppUser(AppUser appUser);

    DeviceGroup findTopByGroupName(String name);

    DeviceGroup findTopById(Long id);

}
