package com.sucheon.box.server.app.model.device;

import com.alibaba.fastjson.annotation.JSONField;
import com.sucheon.box.server.app.model.base.BaseEntity;
import com.sucheon.box.server.app.model.user.AppUser;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
/**
 * 设备
 */
public class Device extends BaseEntity {
    private String openId;
    private String clientId;
    private String deviceName;
    private String deviceDescribe;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String barCode;
    private boolean isOnline = false;
    private String ipAddress;
    //0: deny, 1: allow
    private Integer allow = 1;
    //1: subscribe, 2: publish, 3: pubsub
    private Integer access = 3;
    @OneToOne(targetEntity = Location.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Location location;

    @ManyToOne(targetEntity = DeviceGroup.class, fetch = FetchType.LAZY)
    private DeviceGroup deviceGroup;



    @OneToMany(targetEntity = AudioData.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AudioData> audioDataList;

    private String topic;
    @JSONField(serialize = false)
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    private AppUser appUser;


    private Date lastActiveDate;


    public Date getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(Date lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDescribe() {
        return deviceDescribe;
    }

    public void setDeviceDescribe(String deviceDescribe) {
        this.deviceDescribe = deviceDescribe;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getAllow() {
        return allow;
    }

    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Location getLocation() {
        return location;
    }

    public DeviceGroup getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<AudioData> getAudioDataList() {
        return audioDataList;
    }

    public void setAudioDataList(List<AudioData> audioDataList) {
        this.audioDataList = audioDataList;
    }
}
