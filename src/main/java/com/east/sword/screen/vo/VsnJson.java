package com.east.sword.screen.vo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 构建大屏显示 vsn 文件
 *
 * @CreateDate 17:35 2020/2/18.
 * @Author ZZD
 */
public class VsnJson {

    public static final String VSN_PIC = "2";

    /**
     * 获取vsn json
     *
     * @param width
     * @param height
     * @param type
     * @param picFilePath
     * @return
     */
    public static JsonObject getVsn(String type, String vsnName, String picName, String picFilePath) throws IOException {
        if (vsnName.indexOf(".") != -1) {
            vsnName = vsnName.substring(0,vsnName.indexOf("."));
        }
        File srcImgFile = new File(picFilePath);//得到文件
        Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
        int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
        int srcImgHeight = srcImg.getHeight(null);//获取图片的高

        JsonObject region = new JsonObject();
        JsonObject rectJson = getRect(String.valueOf(srcImgWidth), String.valueOf(srcImgHeight));
        region.add("Rect", rectJson);

        JsonArray jsonArray = new JsonArray();
        JsonObject itemJson = getItem(type, vsnName, picName);
        jsonArray.add(itemJson);
        region.add("Items", jsonArray);

        JsonArray regionsArray = new JsonArray();
        regionsArray.add(region);

        JsonObject page = new JsonObject();
        page.add("Regions",regionsArray);

        JsonArray pages = new JsonArray();
        pages.add(page);

        JsonObject program = new JsonObject();
        program.add("Pages", pages);

        JsonObject programs = new JsonObject();
        programs.add("Program", program);

        JsonObject vsn = new JsonObject();
        vsn.add("Programs", programs);

        return vsn;

    }

    /**
     * item json 对象
     *
     * @param filePath
     * @param type
     * @return
     */
    public static JsonObject getItem(String type, String vsnName, String picName) {
        JsonObject itemObject = new JsonObject();
        itemObject.addProperty("Type", type);


        //图片文件名,路径必须是“节目名.files”
        JsonElement fileSource = new JsonObject();
        String vsnDescribe = "." + File.separator + vsnName + ".files" + File.separator + picName;
        fileSource.getAsJsonObject().addProperty("IsRelative","1");
        fileSource.getAsJsonObject().addProperty("FilePath", vsnDescribe);

        itemObject.add("FileSource", fileSource);
        return itemObject;
    }

    /**
     * rect json 对象
     *
     * @param width
     * @param height
     * @return
     */
    public static JsonObject getRect(String width, String height) {
        JsonObject rectObject = new JsonObject();
        rectObject.addProperty("X", "0");
        rectObject.addProperty("Y", "0");
        rectObject.addProperty("Width", width);
        rectObject.addProperty("Height", height);
        return rectObject;
    }
}
