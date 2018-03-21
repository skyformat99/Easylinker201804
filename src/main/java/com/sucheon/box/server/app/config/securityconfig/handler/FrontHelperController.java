package com.sucheon.box.server.app.config.securityconfig.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 辅助前端
 */
@RestController
@RequestMapping("/front")
public class FrontHelperController {

    @RequestMapping(value = "/getCurrentUserInfo", method = RequestMethod.GET)

    public JSONObject getCurrentUserInfo() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject returnJson = new JSONObject();

        JSONObject userJson = new JSONObject();
        userJson.put("username", appUser.getUsername());
        userJson.put("avatar", appUser.getAvatar());
        userJson.put("email", appUser.getEmail());
        JSONArray jsonArray = new JSONArray();
        for (GrantedAuthority grantedAuthority : appUser.getAuthorities()) {
            jsonArray.add(grantedAuthority.getAuthority());
        }
        userJson.put("authorities", jsonArray);
        userJson.put("phone", appUser.getPhone());
        userJson.put("username", appUser.getUsername());
        returnJson.put("state", 1);
        returnJson.put("data", userJson);
        returnJson.put("message", "获取成功!");
        return returnJson;
    }
}
