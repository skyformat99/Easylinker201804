package com.sucheon.box.server.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.constants.result.ReturnResult;
import com.sucheon.box.server.app.model.user.AppUser;
import com.sucheon.box.server.app.model.user.UserRole;
import com.sucheon.box.server.app.service.AppUserService;
import com.sucheon.box.server.app.service.UserRoleService;
import com.sucheon.box.server.app.utils.EmailSender;
import com.sucheon.box.server.app.utils.MD5Generator;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * 用户基本操作
 * 注册 登陆 修改
 */
@RestController
@RequestMapping("/user")
public class UserRegisterEtcController {
    static final String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    static final String RULE_PHONE = "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}";
    @Autowired
    AppUserService appUserService;

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    EmailSender emailSender;


    /**
     * 注册用户
     *
     * @return
     */
    @RequestMapping("/register")
    public JSONObject register(@RequestBody JSONObject userBody) {

        String username = userBody.getString("username");
        String password = userBody.getString("password");
        String email = userBody.getString("email");
        String phone = userBody.getString("phone");

        if (username == null || password == null || email == null || phone == null) {
            return ReturnResult.returnTipMessage(0, "参数不全!");
        } else if (appUserService.getAAppUserWithParameter(username) != null) {
            return ReturnResult.returnTipMessage(0, "该用户名已经被注册!");

        } else if (appUserService.getAAppUserWithParameter(phone) != null) {
            return ReturnResult.returnTipMessage(0, "该电话号已经被注册!");

        } else if (appUserService.getAAppUserWithParameter(email) != null) {
            return ReturnResult.returnTipMessage(0, "该邮箱已经被注册!");


        } else {//处理邮箱 电话号码格式
            if ((!email.matches(RULE_EMAIL))) {
                return ReturnResult.returnTipMessage(0, "邮箱格式错误!");

            } else if ((!phone.matches(RULE_PHONE))) {
                return ReturnResult.returnTipMessage(0, "电话号码格式错误!");

            } else {//所有的非法条件过滤以后，开始注册用户
                AppUser appUser = new AppUser();
                appUser.setUsername(username);
                appUser.setPassword(MD5Generator.EncodingMD5(password));
                appUser.setEmail(email);
                appUser.setPhone(phone);
                //添加权限  默认新用户全部是 ROLE_USER 普通用户
                UserRole userRole = new UserRole();
                userRole.setAppUser(appUser);
                userRole.setRole("ROLE_USER");
                try {
                    emailSender.sendActiveUserAccountMail(appUser);
                    userRoleService.save(userRole);
                    appUserService.save(appUser);
                    return ReturnResult.returnTipMessage(1, "注册成功!");
                } catch (Exception e) {

                    if (e instanceof SMTPAddressFailedException)
                        return ReturnResult.returnTipMessage(0, "邮箱无效！请使用正确的邮箱!");
                    return ReturnResult.returnTipMessage(0, "邮箱无效！");
                }

            }
        }
    }

    /**
     * 更新用户
     *
     * @param userBody
     * @return
     */
    @RequestMapping(value = "/updateUser")
    public JSONObject updateUser(@RequestBody JSONObject userBody) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = userBody.getString("password");
        String email = userBody.getString("email");
        String phone = userBody.getString("phone");

        if (password == null || email == null || phone == null) {
            return ReturnResult.returnTipMessage(0, "参数不全!");
        } else if (appUserService.getAAppUserWithParameter(phone) != null) {
            return ReturnResult.returnTipMessage(0, "该电话号已经被注册!");
        } else if (appUserService.getAAppUserWithParameter(email) != null) {
            return ReturnResult.returnTipMessage(0, "该邮箱已经被注册!");

        }
        if ((!email.matches(RULE_EMAIL))) {
            return ReturnResult.returnTipMessage(0, "邮箱格式错误!");

        } else if ((!phone.matches(RULE_PHONE))) {
            return ReturnResult.returnTipMessage(0, "电话号码格式错误!");

        } else {//所有的非法条件过滤以后，开始注册用户
            appUser.setPassword(MD5Generator.EncodingMD5(password));
            appUser.setEmail(email);
            appUser.setPhone(phone);
            appUserService.save(appUser);
            return ReturnResult.returnTipMessage(1, "用户信息更新成功");
        }
    }

    /**
     * 激活用户
     *
     * @param activeCode username 的Base64
     * @return
     */
    @RequestMapping("/activeUser/{activeCode}")
    public JSONObject activeUser(@PathVariable String activeCode) {
        String username = new String(Base64.getDecoder().decode(activeCode.getBytes()));
        AppUser appUser = appUserService.getAAppUserByUsername(username);
        if (appUser == null) {
            return ReturnResult.returnTipMessage(0, "用户不存在!");
        } else {
            appUser.setEnabled(true);
            appUserService.save(appUser);
            return ReturnResult.returnTipMessage(1, "激活成功!");
        }
    }

    @RequestMapping("/sendActiveMailAgain/{activeCode}")
    public JSONObject sendActiveMailAgain(@PathVariable String activeCode) {
        String username = new String(Base64.getDecoder().decode(activeCode.getBytes()));
        AppUser appUser = appUserService.getAAppUserByUsername(username);
        if (appUser == null) {
            return ReturnResult.returnTipMessage(0, "用户不存在!");
        } else {
            try {
                emailSender.sendActiveUserAccountMail(appUser);
                return ReturnResult.returnTipMessage(1, "邮件发送成功!");
            } catch (Exception e) {
                return ReturnResult.returnTipMessage(0, "邮件发送失败!");
            }
        }

    }

}
