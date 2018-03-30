package com.easylinker.proxy.server.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.constants.result.ReturnResult;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.Properties;

/**
 * 系统信息获取
 */
@RestController
@RequestMapping(value = "/system")
public class SystemController {
    /**
     * 获取当前服务器的一些状态
     *
     * @return
     */
    @RequestMapping(value = "/getSystemInfo", method = RequestMethod.GET)
    public JSONObject getSystemInfo() {
        JSONObject systemProperty = new JSONObject();
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Properties sysProperty = System.getProperties(); //系统属性
        systemProperty.put("java.version", sysProperty.getProperty("java.version"));
        systemProperty.put("java.vendor", sysProperty.getProperty("java.vendor"));
        systemProperty.put("java.home", sysProperty.getProperty("java.home").replace("\\","_"));
        systemProperty.put("java.vm.version", sysProperty.getProperty("java.vm.version"));
        systemProperty.put("os.name", sysProperty.getProperty("os.name"));
        systemProperty.put("os.arch", sysProperty.getProperty("os.arch"));
        systemProperty.put("os.version", sysProperty.getProperty("os.version"));
        systemProperty.put("sun.cpu.isalist", sysProperty.getProperty("sun.cpu.isalist"));
        systemProperty.put("total.ram", mem.getTotalPhysicalMemorySize() / 1024 / 1024 + "MB");
        systemProperty.put("available.ram", mem.getFreePhysicalMemorySize() / 1024 / 1024 + "MB");
        systemProperty.put("totalMemory", mem.getFreePhysicalMemorySize() / 1024 / 1024 + "MB");
        systemProperty.put("freeMemory", Runtime.getRuntime().freeMemory() / 1024 + "MB");
        systemProperty.put("maxMemory", Runtime.getRuntime().maxMemory() / 1024 + "MB");
        return ReturnResult.returnDataMessage(1, "获取成功!", systemProperty);
    }

}
