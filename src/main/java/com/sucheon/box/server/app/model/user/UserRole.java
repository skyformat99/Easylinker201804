package com.sucheon.box.server.app.model.user;

import com.sucheon.box.server.app.model.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class UserRole extends BaseEntity implements Serializable {

    @ManyToOne(targetEntity = AppUser.class )
    AppUser appUser;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}