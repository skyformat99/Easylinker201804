package com.easylinker.proxy.server.app.utils;

import com.easylinker.proxy.server.app.constants.authority.CommonRole;
import com.easylinker.proxy.server.app.model.user.AppUser;
import com.easylinker.proxy.server.app.model.user.UserRole;

/**
 * 辅助生成ROLE的
 */
public class UserRoleTool {

    /**
     * 生成新角色
     *
     * @param appUser
     * @param commonRole
     * @return
     */
    public static UserRole createRole(AppUser appUser, CommonRole commonRole) {
        UserRole userRole = new UserRole();
        userRole.setAppUser(appUser);
        userRole.setRole(commonRole.toString());
        return userRole;
    }


}
