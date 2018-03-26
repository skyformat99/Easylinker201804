package com.sucheon.box.server.app.config.upyunconfig;

import main.java.com.UpYun;
import main.java.com.upyun.UpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * 又拍云的配置
 */
@Configuration
public class UpYunConfig {
    @Value("${upyun.account.username}")
    String username;
    @Value("${upyun.account.password}")
    String password;
    @Value("${upyun.account.apiKey}")
    String apiKey;
    @Value("${upyun.account.bucketname}")
    String bucketName;

    @Bean
    public UpYun configUpYun() {
        UpYun upyun = new UpYun(bucketName, username, password);

        upyun.setApiDomain(UpYun.ED_AUTO);
        return upyun;
    }

}
