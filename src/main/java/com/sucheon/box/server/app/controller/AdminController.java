package com.sucheon.box.server.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.constants.result.ReturnResult;
import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.model.device.DeviceGroup;
import com.sucheon.box.server.app.model.device.Location;
import com.sucheon.box.server.app.model.user.AppUser;
import com.sucheon.box.server.app.service.DeviceGroupService;
import com.sucheon.box.server.app.service.DeviceService;
import com.sucheon.box.server.app.service.LocationService;
import com.sucheon.box.server.app.utils.Image2Base64Tool;
import com.sucheon.box.server.app.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (deviceName == null || deviceDescribe == null) {
            return ReturnResult.returnTipMessage(0, "参数不全，请检查参数后提交!");
        } else {
            AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //北纬N39°54′6.74″ 东经E116°23′29.52″
            //默认设备位置天安门广场
            Device device = new Device();
            DeviceGroup deviceGroup = new DeviceGroup();
            Location location = new Location();

            location.setDevice(device);
            location.setLatitude("E116°23′29.52″");
            location.setLongitude("N39°54′6.74″");
            location.setLocationDescribe("北京市天安门广场");
            locationService.save(location);//先保存位置

            deviceGroup.setAppUser(null);
            deviceGroup.setComment("普通用户");
            deviceGroup.setGroupName("DEFAULT_GROUP");
            deviceGroupService.save(deviceGroup);//保存分组

            device.setDeviceGroup(deviceGroup);
            device.setLocation(location);
            device.setDeviceName(deviceName);
            device.setDeviceDescribe(deviceDescribe);
            device.setClientId(device.getId().toString());
            //设置ACL 默认
            //"IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/123145315436


            device.setTopic("IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/" + device.getId());
            device.setBarCode(Image2Base64Tool.imageToBase64String(
                    QRCodeGenerator.string2BarCode(device.getId().toString()))
            );
            device.setOpenId(device.getId().toString());
            deviceService.save(device);
            return ReturnResult.returnTipMessage(1, "设备创建成功!");
        }
    }

    /**
     * 批量增加设备
     *
     * @param deviceBody
     * @return
     */
    @RequestMapping("/batchAddADevice")
    public JSONObject batchAddADevice(@RequestBody JSONObject deviceBody) {
        /**
         * {
         "deviceSum":10,
         "deviceNamePrefix" :"前缀",
         "latitude" :"N39°54′6.74″",
         "longitude" : "E116°23′29.52″",
         "locationDescribe" :"厦门科技园",
         "groupName" :"园区使用1组"
         }
         */
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
        } else {
            AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            //开始批量生产
            for (int i = 0; i < deviceSum; i++) {
                //北纬N39°54′6.74″ 东经E116°23′29.52″
                //默认设备位置天安门广场
                Device device = new Device();
                Location location = new Location();
                location.setDevice(device);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                location.setLocationDescribe(locationDescribe + "_Auto_Batch_Product");
                locationService.save(location);//先保存位置
                DeviceGroup deviceGroup = new DeviceGroup();
                deviceGroup.setAppUser(null);
                deviceGroup.setComment("普通用户");
                deviceGroup.setGroupName("DEFAULT_GROUP");
                deviceGroupService.save(deviceGroup);//保存分组
                device.setDeviceGroup(deviceGroup);
                device.setLocation(location);
                device.setLastActiveDate(new Date());
                device.setDeviceName(deviceNamePrefix + "_Auto_Batch_Product_Num_" + i);
                device.setDeviceDescribe("SucheonBox_Auto_Batch_Product_Num_" + i);
                device.setClientId(device.getId().toString());
                //设置ACL  默认值
                device.setTopic("IN/DEVICE/DEFAULT_USER/DEFAULT_GROUP/" + device.getId());
                device.setBarCode(Image2Base64Tool.imageToBase64String(
                        QRCodeGenerator.string2BarCode(device.getId().toString()))
                );
                device.setOpenId(device.getId().toString());
                deviceService.save(device);
            }
            return ReturnResult.returnTipMessage(1, "批量添加设备成功!");
        }

    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable("id") Long id) {
        Device device = deviceService.findADevice(id);
        if (device != null) {
            deviceService.delete(device);
            return ReturnResult.returnTipMessage(0, "设备删除成功!");

        } else {
            return ReturnResult.returnTipMessage(0, "设备不存在!");
        }
    }
}
