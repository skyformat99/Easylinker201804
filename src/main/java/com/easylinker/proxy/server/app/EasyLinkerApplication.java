package com.easylinker.proxy.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableWebSecurity
@EnableAsync
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class EasyLinkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyLinkerApplication.class, args);
	}
}
