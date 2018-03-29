package com.easylinker.proxy.server.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业管理控制器 :ROLE_ENTERPRISE
 * 企业用户可以自己创建设备
 *
 */
@RestController
@PreAuthorize(value = "hasRole('ROLE_ENTERPRISE') AND hasRole('ROLE_USER')")
@RequestMapping("/enterprise")
public class EnterpriseUserController {






}
