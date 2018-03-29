package com.easylinker.proxy.server.app.utils;

import com.easylinker.proxy.server.app.model.device.Device;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarCodeGenerator {
    public static void main(String[] args) {
        Device device = new Device();
        device.setDeviceName("仓库的盒子");
        device.setDeviceDescribe("仓库的盒子");
        device.setOpenId("1234412423115");
        createBarCodeImage(device);
    }

    public static void createBarCodeImage(Device device) {
        int width = 600;
        int height = 200;
        File file = new File("./createImage.png");
        Font font = new Font("宋体", Font.BOLD, 26);
        BufferedImage bufferedImage = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0, 0, width, height);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.setFont(font);
        FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds("中", fontRenderContext);

        try {
            graphics2D.drawImage(ImageIO.read(new File("F:\\Image\\1.jpg"))
                            .getScaledInstance(width / 4, height, Image.SCALE_AREA_AVERAGING),
                    0, 0, null);
            graphics2D.drawString(device.getDeviceName(), width / 4 + 5, (int) stringBounds.getBounds().getHeight() + 40);
            graphics2D.drawString(device.getDeviceDescribe(), width / 4 + 5, (int) stringBounds.getBounds().getHeight() + 80);
            graphics2D.drawString(device.getOpenId(), width / 4 + 5, (int) stringBounds.getBounds().getHeight() + 120);
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            System.out.println("图片生成失败!");
        }

    }
}
