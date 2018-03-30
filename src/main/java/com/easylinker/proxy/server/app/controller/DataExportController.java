package com.easylinker.proxy.server.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.easylinker.proxy.server.app.model.device.Device;
import com.easylinker.proxy.server.app.constants.result.ReturnResult;
import com.easylinker.proxy.server.app.service.DeviceService;
import main.java.com.UpYun;
import main.java.com.upyun.UpException;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataExportController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    UpYun upYun;

    /**
     * 导出数据到又拍云
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)

    /**
     * --------------------------
     * -OPENID  |Name|备注|二维码|
     * -------------------------
     * |1322342|Box1|etc|Base64|
     * ------------------------
     *|1322342|Box1|etc|Base64|
     *------------------------
     */

    public JSONObject exportExcel() throws IOException {
        List<Device> deviceList = deviceService.findAllDevice();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("设备表");
        int rowNum = 1;
        String[] headers = {"OPENID", "名称", "备注", "二维码"};
        HSSFRow hssfRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = hssfRow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        for (Device device : deviceList) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(device.getOpenId().toString());
            row.createCell(1).setCellValue(device.getDeviceName());
            row.createCell(2).setCellValue(device.getDeviceDescribe());
            row.createCell(3).setCellValue(device.getBarCode());
            rowNum++;
        }
        String filename = new Date().toString().replace(" ", "_").replace(":", "-") + "_export.xls";
        File excel = new File(filename);
        workbook.write(new FileOutputStream(excel));

        try {
            /**
             * writeFile(String filePath, File file, boolean auto)
             * 有个坑：又拍云的SDK里面，必须手动指定文件名，而不是去读取参数2的文件名.
             */
            boolean isOjbk = upYun.writeFile("/data_export/" + filename, new File(filename), true);
            if (isOjbk) {
                excel.delete();
                return ReturnResult.returnTipMessage(1, "数据上传成功!");
            } else {
                return ReturnResult.returnTipMessage(0, "数据上传失败!");
            }

        } catch (UpException e) {
            return ReturnResult.returnTipMessage(0, "数据上传异常!");
        }
    }
}
