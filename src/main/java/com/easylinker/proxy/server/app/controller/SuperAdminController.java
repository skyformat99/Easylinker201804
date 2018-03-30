package com.easylinker.proxy.server.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 超级管理员业务控制器
 */
@RestController
@RequestMapping("/superAdmin")
@PreAuthorize(value = "hasRole('ROLE_SUPER_ADMIN') AND  hasRole('ROLE_USER')")

public class SuperAdminController {
}
