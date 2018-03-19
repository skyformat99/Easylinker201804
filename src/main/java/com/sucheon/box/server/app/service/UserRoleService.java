package com.sucheon.box.server.app.service;

import com.sucheon.box.server.app.dao.UserRoleRepository;
import com.sucheon.box.server.app.model.user.AppUser;
import com.sucheon.box.server.app.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserRoleService")
public class UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;

    public List<UserRole> getAppUserRoles(AppUser appUser) {

        return userRoleRepository.findAllByAppUser(appUser);

    }

    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

}
