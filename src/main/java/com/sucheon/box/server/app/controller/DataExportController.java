package com.sucheon.box.server.app.controller;

import com.sucheon.box.server.app.model.device.Device;
import com.sucheon.box.server.app.service.DeviceService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/data")
public class DataExportController {
    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)

    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Device> deviceList = deviceService.findAllDevice();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("设备表");
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"OPENID", "名称", "备注", "二维码"};
        //headers表示excel表中第一行的表头
        HSSFRow hssfRow = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = hssfRow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        StringBuffer stringBuffer = new StringBuffer();
        //在表中存放查询到的数据放入对应的列
        for (Device device : deviceList) {
            stringBuffer.append("<br><img width=\"50px\" src=\"" + device.getBarCode() + "\"/>");
            stringBuffer.append("名称" + device.getDeviceName());
            HSSFRow row = sheet.createRow(rowNum);

            row.createCell(0).setCellValue(device.getId());
            row.createCell(1).setCellValue(device.getDeviceName());
            row.createCell(2).setCellValue(device.getDeviceDescribe());
            row.createCell(3).setCellValue(device.getBarCode());
            rowNum++;
        }

//
//        response.setHeader("content-Type", "application/vnd.ms-excel");
//        response.setHeader("Content-disposition", "attachment;filename=sheet.xls");
//        response.flushBuffer();
//        workbook.write(response.getOutputStream());
        //response.getWriter().write(stringBuffer.toString());
        File file = new File("D:\\cc.html");
        file.createNewFile();
        PrintWriter printWriter = new PrintWriter(new DataOutputStream(new FileOutputStream(file)));
        printWriter.write(stringBuffer.toString());
        printWriter.flush();
        printWriter.close();


    }


}
