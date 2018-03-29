package com.easylinker.proxy.server.app.service;

import com.easylinker.proxy.server.app.dao.UserRoleRepository;
import com.easylinker.proxy.server.app.model.user.AppUser;
import com.easylinker.proxy.server.app.model.user.UserRole;
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
