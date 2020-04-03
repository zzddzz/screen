package com.east.sword.screen.util;

import lombok.extern.slf4j.Slf4j;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 添加水印工具类
 * @CreateDate 21:13 2020/2/19.
 * @Author ZZD
 */
@Slf4j
public class WaterMarkUtils {

    private static Font fontTemp = new Font("微软雅黑", Font.PLAIN, 30);//水印字体
    private static String windowsRtnLine = "\n";

    private static Color colorWhite = new Color(255,255,255,255); //水印图片色彩以及透明度
    private static Color colorRed = Color.RED; //水印图片色彩以及透明度
    private static Color colorBlue = new Color(63,72,204,255); //水印图片色彩以及透明度

    //todo 文字多少的问题，超出背景图片怎么办

    /**
     * @param srcImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @param markContentColor 水印颜色
     * @param font 水印字体
     */
    public static void createWaterMark(File srcImgFile, String tarImgPath, String waterMarkContent,int size) {

        //设置字体
        Font font = new Font("微软雅黑", Font.BOLD, size);//水印字体

        try {
            // 源图片信息
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(colorRed); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            int fontHeight = getFontHeight(g,font);

            //文本按照段落切分
            String[] sectionArr = waterMarkContent.split(windowsRtnLine);
            for (int i=0;i<sectionArr.length;i++) {
                String line = sectionArr[i];

                FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);


                //设置水印的坐标 居中对齐
                int x = (srcImgWidth - getWatermarkLength(line, g))/2;
                int y = (srcImgHeight - (fontHeight * sectionArr.length) )/2 + fontHeight * (i+1);

                //画出水印
                g.drawString(line, x, y);
            }
            g.dispose();

            // 输出图片
            String srcImgName = srcImgFile.getName();
            String type = srcImgName.substring(srcImgName.lastIndexOf(".") + 1,srcImgName.length());

            //存在文件先删除
            File file = new File(tarImgPath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, type, outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            log.error("添加水印异常：{}",e);
        }
    }

    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    public static int getFontHeight(Graphics2D g2d, Font font) {
        // 设置大字体
        FontRenderContext context = g2d.getFontRenderContext();
        // 获取字体的像素范围对象
        Rectangle2D stringBounds = font.getStringBounds("w", context);
        double fontWidth = stringBounds.getWidth();
        int fontHight = (int)fontWidth;

        return fontHight + fontHight/4 ;//再字体高度上 加上1/4 的字体高度
    }
}
