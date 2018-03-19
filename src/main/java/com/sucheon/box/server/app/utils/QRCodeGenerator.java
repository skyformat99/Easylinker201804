package com.sucheon.box.server.app.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by wwhai on 2017/10/5.
 * 二维码生成器
 */
public class QRCodeGenerator {
    public static final int WIDTH = 150;
    public static final int HEIGHT = 150;
    private static int ON_COLOR = 0xFF000000;     //前景色
    private static int BACK_COLOR = 0xFFFFFFFF;    //背景色

    public static File string2BarCode(String deviceId) {
        File file = null;
        try {
            file = new File("./tempBarCodeImage.png");
            OutputStream outputStream = new FileOutputStream(file);
            QRCodeWriter writer = new QRCodeWriter();
            //http://localhost/device/barCode/2423de45t46g465
            // //通过二维码的HTTP请求 就可以获取数据
            BitMatrix matrix = writer.encode("/device/barCode/" + deviceId, BarcodeFormat.QR_CODE, HEIGHT, WIDTH);
            MatrixToImageConfig config = new MatrixToImageConfig(ON_COLOR, BACK_COLOR);

            MatrixToImageWriter.writeToStream(matrix, "png", outputStream, config);

        } catch (Exception e) {
            System.out.println("二维码生成失败!");

        }
        return file;
    }

    /**
     * 读二维码并输出携带的信息
     */
    public static String readQrCode(String path) throws IOException {
        BufferedImage image = ImageIO.read(new FileInputStream(new File(path)));
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap);
        } catch (ReaderException e) {
            e.printStackTrace();
        }
        return result.getText();
    }


//    public static void main(String[] args) {
//        System.out.println(Image2Base64Tool.imageToBase64String(string2BarCode("wwhai")));
//
//    }

}