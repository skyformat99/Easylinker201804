package com.sucheon.box.server.app.utils;

import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;


    public void sendActiveUserAccountMail(AppUser appUser) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("751957846@qq.com");
        message.setSentDate(new Date());
        message.setTo(appUser.getEmail());
        message.setSubject("[硕橙科技-激活账户]");
        message.setText("欢迎注册硕橙盒子!\n请点击下方链接激活账户:http://localhost/user/active/" + Base64.getEncoder().encodeToString(appUser.getUsername().getBytes()));
        mailSender.send(message);

    }

    public void sendHtmlMail() {


    }

    public void sendToGroup() {

    }
}
