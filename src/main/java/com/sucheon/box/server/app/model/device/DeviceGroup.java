package com.sucheon.box.server.app.model.device;

import com.sucheon.box.server.app.model.base.BaseEntity;
import com.sucheon.box.server.app.model.user.AppUser;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * 新的SQL
 * select allow,ip_address, open_id as username,client_id AS clientid,access, topic
 * from device
 * where  open_id ='4a7cd1a5ad1559944d0b09cfb537bcae';
 * <p>
 * CREATE TABLE `mqtt_acl` (
 * `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 * `allow` int(1) DEFAULT NULL COMMENT '0: deny, 1: allow',
 * `ipaddr` varchar(60) DEFAULT NULL COMMENT 'IpAddress',
 * `username` varchar(100) DEFAULT NULL COMMENT 'Username',
 * `clientid` varchar(100) DEFAULT NULL COMMENT 'ClientId',
 * `access` int(2) NOT NULL COMMENT '1: subscribe, 2: publish, 3: pubsub',
 * `topic` varchar(100) NOT NULL DEFAULT '' COMMENT 'Topic Filter',
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Entity
/**
 * 设备组
 */
public class DeviceGroup extends BaseEntity {

    private String groupName;
    private String comment;
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @NotFound(action= NotFoundAction.IGNORE)
    private AppUser appUser;

    @OneToMany(targetEntity = Device.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Device> deviceList;

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
