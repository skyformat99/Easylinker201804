package com.sucheon.box.server.app.model.device;

import com.sucheon.box.server.app.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 设备数据
 */
@Entity
public class DeviceData extends BaseEntity {
    private String data;
    @ManyToOne(targetEntity = Device.class, fetch = FetchType.LAZY)
    private Device device;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
