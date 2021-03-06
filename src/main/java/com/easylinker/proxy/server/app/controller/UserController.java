package com.easylinker.proxy.server.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.model.device.DeviceGroup;
import com.easylinker.proxy.server.app.model.user.AppUser;
import com.easylinker.proxy.server.app.constants.result.ReturnResult;
import com.easylinker.proxy.server.app.service.AppUserService;
import com.easylinker.proxy.server.app.service.DeviceGroupService;
import com.easylinker.proxy.server.app.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize(value = "hasRole('ROLE_USER')")
/**
 * 用户的业务逻辑层
 */
public class UserController {

    @Autowired
    AppUserService appUserService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    DeviceGroupService deviceGroupService;

    /**
     * 把单个设备绑定到用户
     *
     * @param deviceId
     * @return
     */

    @RequestMapping(value = "/bind/{deviceId}")
    public JSONObject bind(@PathVariable Long deviceId) {
        Device device = deviceService.findADevice(deviceId);
        if (device != null) {
            if (device.getAppUser() == null) {
                AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                device.setAppUser(appUser);
                deviceService.save(device);
                return ReturnResult.returnTipMessage(1, "设备绑定成功!");
            } else {
                return ReturnResult.returnTipMessage(0, "设备已经绑定!");

            }


        } else {
            return ReturnResult.returnTipMessage(1, "设备不存在!");

        }
    }

    /**
     * 增加一个分组
     * 参数
     * 分组名字：设备组必须用英文或者数字组合且不下6位!
     * 分组简介
     *
     * @return
     */
    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public JSONObject addAGroup(@RequestBody JSONObject body) {
        String groupName = body.getString("groupName");
        String comment = body.getString("comment");
        if (groupName == null || comment == null) {
            return ReturnResult.returnTipMessage(0, "请求参数不完整!");
        } else if (!groupName.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}")) {
            return ReturnResult.returnTipMessage(0, "设备组必须用英文或者数字组合且不下6位!");
        } else if (deviceGroupService.getADeviceGroupByName(groupName) == null) {
            AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            DeviceGroup deviceGroup = new DeviceGroup();
            deviceGroup.setGroupName(groupName);
            deviceGroup.setComment(comment);
            deviceGroup.setAppUser(appUser);
            deviceGroupService.save(deviceGroup);
            return ReturnResult.returnTipMessage(0, "分组增加成功!");
        } else {
            return ReturnResult.returnTipMessage(0, "分组名称已经存在!");

        }
    }


    /**
     * 改变设备的分组
     *
     * @return
     */
    @RequestMapping(value = "/changeDeviceGroup", method = RequestMethod.POST)
    public JSONObject changeDeviceGroup(@RequestBody JSONObject body) {
        Long deviceId = body.getLongValue("deviceId");
        String groupName = body.getString("groupName");
        String comment = body.getString("comment");
        if (groupName == null || comment == null || deviceId == null) {
            return ReturnResult.returnTipMessage(0, "请求参数不完整!");
        } else if (!groupName.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}")) {
            return ReturnResult.returnTipMessage(0, "设备组必须用英文或者数字组合且不下6位!");

        } else {
            Device device = deviceService.findADevice(deviceId);
            DeviceGroup deviceGroup = deviceGroupService.findADeviceGroupByName(groupName);
            if (device != null) {
                if (deviceGroup == null) {
                    AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    DeviceGroup group = new DeviceGroup();
                    group.setGroupName(groupName);
                    group.setComment(comment);
                    group.setAppUser(appUser);
                    deviceGroupService.save(group);
                    device.setDeviceGroup(deviceGroup);
                    deviceService.save(device);
                    return ReturnResult.returnTipMessage(1, "分配新分组成功!");
                } else {
                    device.setDeviceGroup(deviceGroup);
                    deviceService.save(device);
                    return ReturnResult.returnTipMessage(1, "分配已有的分组成功!");

                }
            } else {
                return ReturnResult.returnTipMessage(0, "设备不存在!");
            }
        }


    }

    /**
     * 获取所有分组
     *
     * @return
     */

    @RequestMapping(value = "/getALlGroups", method = RequestMethod.GET)
    public JSONObject getALlGroups() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        JSONObject returnJson = new JSONObject();
        returnJson.put("state", 1);
        returnJson.put("message", "查询成功!");
        returnJson.put("data", deviceGroupService.getAllByAppUser(appUser));
        return returnJson;

    }


    /**
     * 用户获取所有的设备
     *
     * @return
     */
    @RequestMapping(value = "/getAllDevices/{page}/{size}", method = RequestMethod.GET)
    public JSONObject getAllDevices(@PathVariable int page, @PathVariable int size) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ReturnResult.returnDataMessage(1, "查询成功!", deviceService.getAllDevicesByAppUser(appUser, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))));

    }

    /**
     * 当前登陆用户根据分组ID查询所有的设备
     *
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/getAllDevicesByGroup/{groupId}/{page}/{size}", method = RequestMethod.GET)
    public JSONObject getAllDevicesByGroup(@PathVariable Long groupId, @PathVariable int page, @PathVariable int size) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DeviceGroup group = deviceGroupService.findADeviceGroupById(groupId);
        return ReturnResult.returnDataMessage(1, "查询成功!", deviceService.getAllDevicesByAppUserAndGroup(appUser, group, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))));


    }


}
