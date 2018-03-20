package com.sucheon.box.server.app.model.device;

import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.model.base.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * 地理位置
 */
@Entity
public class Location extends BaseEntity {
    //Latitude and longitude:经纬度
    private String latitude;
    private String longitude;
    private String locationDescribe;

    @OneToOne(targetEntity = Device.class, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocationDescribe() {
        return locationDescribe;
    }

    public void setLocationDescribe(String locationDescribe) {
        this.locationDescribe = locationDescribe;
    }

    @Override
    public String toString() {
        JSONObject data = new JSONObject();
        data.put("latitude", latitude);
        data.put("latitude", longitude);
        data.put("latitude", locationDescribe);
        return data.toJSONString();
    }
}
