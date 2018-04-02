package com.easylinker.proxy.server.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.model.device.DeviceGroup;
import com.easylinker.proxy.server.app.model.device.Location;
import com.easylinker.proxy.server.app.model.user.AppUser;
import com.easylinker.proxy.server.app.utils.Image2Base64Tool;
import com.easylinker.proxy.server.app.utils.QRCodeGenerator;
import com.easylinker.proxy.server.app.constants.result.ReturnResult;
import com.easylinker.proxy.server.app.service.AppUserService;
import com.easylinker.proxy.server.app.service.DeviceGroupService;
import com.easylinker.proxy.server.app.service.DeviceService;
import com.easylinker.proxy.server.app.service.LocationService;
import com.sun.javafx.geom.transform.BaseTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 普通管理员业务逻辑控制器
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') AND hasRole('ROLE_USER')")
public class AdminController {
    @Autowired
    LocationService locationService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    DeviceGroupService deviceGroupService;
    @Autowired
    AppUserService appUserService;

    /**
     * 管理员增加一个设备
     * 默认增加的设备是没有用户的，后面用户购买设备扫码的时候 再绑定用户
     *
     * @param deviceBody 包含设备信息的JSON
     * @return
     */
    @RequestMapping("/addADevice")
    public JSONObject addADevice(@RequestBody JSONObject deviceBody) {
        String deviceName = deviceBody.getString("deviceName");
        String deviceDescribe = deviceBody.getString("deviceDescribe");
        String deviceNamePrefix = deviceBody.getString("deviceNamePrefix");
        String latitude = deviceBody.getString("latitude");
        String longitude = deviceBody.getString("longitude");
        String locationDescribe = deviceBody.getString("locationDescribe");
        String groupName = deviceBody.getString("groupName");


        if (deviceDescribe == null || deviceName == null || groupName == null || latitude == null || longitude == null || locationDescribe == null || deviceNamePrefix == null) {
            return ReturnResult.returnTipMessage(0, "参数不全!");


        } else if (!groupName.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}")) {
            return ReturnResult.returnTipMessage(0, "设备组必须用英文字幕或者数字组合且不下6位!");
        } else if (!deviceNamePrefix.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}")) {
            return ReturnResult.returnTipMessage(0, "设备名称前缀必须用英文字幕或者数字组合且不下6位!");
        } else {

            Device device = new Device();
            DeviceGroup deviceGroup = new DeviceGroup();
            deviceGroup.setComment(groupName);
            deviceGroup.setGroupName(groupName);
            deviceGroupService.save(deviceGroup);//保存分组
            device.setDeviceGroup(deviceGroup);
            device.setAppUser(null);
            device.setLastActiveDate(new Date());
            device.setDeviceName(deviceNamePrefix + "_" + deviceName);
            device.setDeviceDescribe("Device_" + deviceDescribe);
            device.setClientId(device.getId().toString());
            //设置ACL  默认值
            device.setTopic("IN/DEVICE/DEFAULT_USER/" + deviceGroup.getId() + "/" + device.getId());
            device.setBarCode(Image2Base64Tool.imageToBase64String(QRCodeGenerator.string2BarCode(device.getId().toString())));
            device.setOpenId(device.getId().toString());
            Location location = new Location();
            location.setDevice(device);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setLocationDescribe("位于:[" + locationDescribe + "]的设备!");
            locationService.save(location);//先保存位置
            device.setLocation(location);
            deviceService.save(device);
            return ReturnResult.returnTipMessage(1, "设备创建成功!");
        }
    }

    /**
     * 批量增加设备
     * 批量增加设备不需要指定专门的名称 前缀，自动生成.
     *
     * @param deviceBody
     * @return
     */
    @RequestMapping("/batchAddADevice")
    public JSONObject batchAddADevice(@RequestBody JSONObject deviceBody) {

        int deviceSum = deviceBody.getIntValue("deviceSum");
        String deviceNamePrefix = deviceBody.getString("deviceNamePrefix");
        String latitude = deviceBody.getString("latitude");
        String longitude = deviceBody.getString("longitude");
        String locationDescribe = deviceBody.getString("locationDescribe");
        String groupName = deviceBody.getString("groupName");

        if (groupName == null || latitude == null || longitude == null || locationDescribe == null || deviceNamePrefix == null) {
            return ReturnResult.returnTipMessage(0, "参数不全，请检查参数后提交!");
        } else if (deviceSum <= 0) {
            return ReturnResult.returnTipMessage(0, "至少选择创建一个设备!");


        } else if (!groupName.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}")) {
            return ReturnResult.returnTipMessage(0, "设备组必须用英文字幕或者数字组合且不下6位!");

        } else if (!deviceNamePrefix.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}")) {
            return ReturnResult.returnTipMessage(0, "设备名称前缀必须用英文字幕或者数字组合且不下6位!");
        } else {
            DeviceGroup deviceGroup = new DeviceGroup();
            deviceGroup.setAppUser(null);
            deviceGroup.setComment(groupName);
            deviceGroup.setGroupName(groupName);
            deviceGroupService.save(deviceGroup);//所有新增加的设备，分组一样,保存分组
            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setLocationDescribe("位于:[" + locationDescribe + "]的设备!");
            locationService.save(location);//先保存位置
            for (int i = 0; i < deviceSum; i++) {
                Device device = new Device();
                device.setDeviceGroup(deviceGroup);
                device.setLastActiveDate(new Date());
                device.setDeviceName(deviceNamePrefix + "_Auto_" + i);
                device.setDeviceDescribe("Device_Auto_Batch_Product_Num_" + i);
                device.setClientId(device.getId().toString());
                //设置ACL  默认值
                device.setTopic("IN/DEVICE/DEFAULT_USER/" + deviceGroup.getId() + "/" + device.getId());
                device.setBarCode(Image2Base64Tool.imageToBase64String(QRCodeGenerator.string2BarCode(device.getId().toString())));
                device.setOpenId(device.getId().toString());
                device.setLocation(location);
                deviceService.save(device);
            }
            return ReturnResult.returnTipMessage(1, "批量添加设备成功!");
        }

    }

    /**
     * 删除设备
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable("id") Long id) {
        Device device = deviceService.findADevice(id);
        if (device != null) {
            deviceService.delete(device);
            return ReturnResult.returnTipMessage(1, "设备删除成功!");

        } else {
            return ReturnResult.returnTipMessage(0, "设备不存在!");
        }
    }

    /**
     * 管理员查看所有设备
     *
     * @return
     */
    @RequestMapping(value = "/getAllDevices/{page}/{size}", method = RequestMethod.GET)
    public JSONObject getAllDevices(@PathVariable int page, @PathVariable int size) {
        return ReturnResult.returnDataMessage(1, "查询成功!",
                deviceService.getAllDevices(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))));

    }

    /**
     * 管理员绑定设备
     *
     * @return
     */
    @RequestMapping(value = "/bindDevicesToUser", method = RequestMethod.POST)
    public JSONObject bindDevicesToUser(@RequestBody JSONObject body) {
        //[111,222,333,4444]->appUser
        Long userId = body.getLongValue("userId");
        Long groupId = body.getLongValue("groupId");
        JSONArray deviceIdArray;
        try {
            deviceIdArray = body.getJSONArray("deviceIdArray");
        } catch (Exception e) {
            return ReturnResult.returnTipMessage(1, "deviceIdArray应该为数组!");

        }

        if (userId == null || deviceIdArray == null || groupId == null) {
            return ReturnResult.returnTipMessage(0, "参数不全!");
        } else {
            int total = deviceIdArray.size();
            int successCount = 0;
            AppUser appUser = appUserService.findAAppUser(userId);
            if (appUser != null) {
                DeviceGroup deviceGroup = deviceGroupService.findADeviceGroupById(groupId);
                if (deviceGroup != null) {
                    for (Object o : deviceIdArray) {
                        Device device = deviceService.findADevice((Long.parseLong(o.toString())));
                        if (device != null && device.getAppUser() == null) {
                            successCount += 1;
                            device.setAppUser(appUser);
                            device.setDeviceGroup(deviceGroup);
                            //更新ACL
                            device.setTopic("IN/DEVICE/" + appUser.getId() + "/" + deviceGroup.getId() + "/" + device.getId());
                            deviceGroupService.save(deviceGroup);
                            deviceService.save(device);
                        }

                    }
                    return ReturnResult.returnTipMessage(1, "结果:总数[" + total + "]成功[" + successCount + "]失败[" + (total - successCount) + "]");

                } else {
                    for (Object o : deviceIdArray) {
                        Device device = deviceService.findADevice((Long.parseLong(o.toString())));
                        if (device != null && device.getAppUser() == null) {
                            successCount += 1;
                            //更新ACL
                            DeviceGroup newGroup = new DeviceGroup();
                            newGroup.setGroupName("group_" + appUser.getUsername());
                            newGroup.setAppUser(appUser);
                            newGroup.setComment("用户:" + appUser.getUsername() + "的分组");
                            deviceGroupService.save(newGroup);
                            device.setAppUser(appUser);
                            device.setDeviceGroup(newGroup);
                            device.setTopic("IN/DEVICE/" + appUser.getId() + "/" + newGroup.getId() + "/" + device.getId());
                            deviceService.save(device);
                        } else {
                            System.out.println("ID:["+o.toString() + "]对应的设备不存在,绑定失败!");
                        }

                    }

                    return ReturnResult.returnTipMessage(1, "结果:总数[" + total + "]成功[" + successCount + "]失败[" + (total - successCount) + "],如果有失败，可能原因:部分设备ID不存在!");
                }


            } else {
                return ReturnResult.returnTipMessage(0, "用户不存在!");

            }
        }


    }

    /**
     * 查看当前所有的用户
     *
     * @return
     */
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)

    public JSONObject getAllUsers() {

        return ReturnResult.returnDataMessage(1, "获取成功!", appUserService.getAllUsers());

    }
}
