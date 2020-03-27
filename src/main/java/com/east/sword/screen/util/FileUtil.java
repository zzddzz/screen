package com.east.sword.screen.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * 文件类
 *
 * @CreateDate 11:47 2020/2/24.
 * @Author ZZD
 */
@Slf4j
public class FileUtil {

    public static String[] picinfo = {"JPG", "jpg", "bmp", "BMP", "jpeg", "JPEG", "gif", "GIF", "png", "PNG"};

    public static void createFile(String filePath, String fileName, String content) {
        try {
            File file = new File(filePath + fileName);
            FileUtils.writeStringToFile(file, content, "UTF-8");
        } catch (Exception e) {
            log.error("生成文件异常:{}", e);
        }

    }

    /**
     * 是否是图片
     *
     * @param file
     * @return
     */
    public static boolean isPic(FTPFile file) {

        int begPos = file.getName().lastIndexOf(".");
        if (begPos == -1) {
            return false;
        }
        String suffix = file.getName().substring(begPos + 1, file.getName().length());
        if (Arrays.asList(picinfo).contains(suffix)) {
            return true;
        }
        return false;
    }

    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 判断图片是否符合预设规则
     *
     * @param originFileName
     * @param regexChar
     * @return
     */
    public static boolean validatePicOfScreen(String originFileName, String regexChar) {
        if (StringUtils.isBlank(regexChar)) {
            return false;
        }
        String[] regexArray = regexChar.split(",");
        for (String regex : regexArray) {
            if (originFileName.indexOf(regex) != -1) {
                return true;
            }
        }
        return false;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
