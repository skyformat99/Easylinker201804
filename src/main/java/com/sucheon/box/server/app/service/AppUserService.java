package com.sucheon.box.server.app.service;

import com.sucheon.box.server.app.dao.AppUserRepository;
import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "AppUserService")
public class AppUserService {
    @Autowired
    AppUserRepository appUserRepository;

    /**
     * @param parameter 表示 UsernameOrEmailOrPhone，三种字段都可以登陆
     * @return 返回一个User Info
     */
    public AppUser getAAppUserWithParameter(String parameter) {
        return appUserRepository.findTopByUsernameOrEmailOrPhone(parameter, parameter, parameter);
    }

    public void save(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public AppUser getAAppUserByUsername(String username) {
        return appUserRepository.findTopByUsername(username);

    }

    public AppUser findAAppUser(Long id) {
        return appUserRepository.getOne(id);
    }


}
