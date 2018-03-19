package com.sucheon.box.server.app.model.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.sucheon.box.server.app.model.base.BaseEntity;
import com.sucheon.box.server.app.model.device.DeviceGroup;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "USER")

public class AppUser extends BaseEntity implements UserDetails {

    private String username;
    @JSONField(serialize = false)
    private String password;
    private String email;
    private String phone;
    private String avatar = "/avatar/avatar" + new Random().nextInt(51) + ".png";
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(targetEntity = DeviceGroup.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DeviceGroup> deviceGroupList;

    @JSONField(serialize = false)
    @OneToMany(targetEntity = UserRole.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRole> roleList;

    public List<UserRole> getRoleList() {
        return roleList;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /**
         * 默认给了一个普通用户
         */
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        for (UserRole role : roleList) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return simpleGrantedAuthorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public List<DeviceGroup> getDeviceGroupList() {
        return deviceGroupList;
    }

    public void setDeviceGroupList(List<DeviceGroup> deviceGroupList) {
        this.deviceGroupList = deviceGroupList;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setRoleList(List<UserRole> roleList) {
        this.roleList = roleList;
    }
}