package com.sucheon.box.server.app.service;

import com.sucheon.box.server.app.model.user.AppUser;
import com.sucheon.box.server.app.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "AppUserUserDetailService")
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    AppUserService appUserService;
    @Autowired
    UserRoleService userRoleService;

    /**
     * @param parameter 可以用Username Or Email Or Phone 登录
     * @return AppUser
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String parameter) throws UsernameNotFoundException {


        AppUser appUser;
        try {
            appUser = appUserService.getAAppUserWithParameter(parameter);
            List<UserRole> roleList = userRoleService.getAppUserRoles(appUser);
            appUser.setRoleList(roleList);
            return appUser;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new UsernameNotFoundException("UserNotFound,Please Check Your Input!");
        }

    }
}
