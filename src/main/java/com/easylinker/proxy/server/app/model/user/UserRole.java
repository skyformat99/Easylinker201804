package com.easylinker.proxy.server.app.model.user;

import com.easylinker.proxy.server.app.model.base.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class UserRole extends BaseEntity implements Serializable {
    @NotFound(action= NotFoundAction.IGNORE)
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