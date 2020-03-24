package com.east.sword.screen.vo;


import com.east.sword.screen.util.HexHelp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 金晓命令格式
 *
 * @CreateDate 10:52 2020/3/18.
 * @Author ZZD
 */
@Slf4j
public class GoldMon {

    public static final String FRAME_HEADER = "02";//帧头
    public static final String FRAME_ADDRESS = "00 00";//帧地址
    public static final String FRAME_FOOTER = "03";//帧尾
    public static final String FRAME_ERROR_INFO = "30 31";//取大屏当前故障
    public static final String FRAME_UPLOAD_FILE = "31 30";//上载文件
    public static final String FRAME_DOWNLOAD_FILE = "30 39";//下载文件
    public static final String FRAME_PLAY_ACTIVE = "39 38";//激活播放列表
    public static final String FRAME_PLAY_INFO = "39 37";//查看播放信息
    public static final String FRAME_LIGHT_SET = "30 33";//亮度设置
    public static final String FRAME_LIGHT_INFO = "30 36";//亮度信息

    public static HashMap<String, byte[]> frameInfo = new HashMap();
    static {
        frameInfo.put(FRAME_HEADER, new byte[]{0x02});
        frameInfo.put(FRAME_ADDRESS, new byte[]{0x00, 0x00});
        frameInfo.put(FRAME_FOOTER, new byte[]{0x03});
        frameInfo.put(FRAME_ERROR_INFO, new byte[]{0x30, 0x31});
        frameInfo.put(FRAME_UPLOAD_FILE, new byte[]{0x31, 0x30});
        frameInfo.put(FRAME_DOWNLOAD_FILE, new byte[]{0x30, 0x39});
        frameInfo.put(FRAME_PLAY_ACTIVE, new byte[]{0x39, 0x38});
        frameInfo.put(FRAME_PLAY_INFO, new byte[]{0x39, 0x37});
        frameInfo.put(FRAME_LIGHT_SET, new byte[]{0x30, 0x33});
        frameInfo.put(FRAME_LIGHT_INFO, new byte[]{0x30, 0x36});
    }

    /**
     * 获取转义字符
     * 0x02 转换为 0x1B 0xE7
     * 0x03 转换为 0x1B 0xE8
     * 0x1B 转换为 0x1B 0x00
     *
     * @param src
     * @return
     */
    public static String getTranInfo(String src) {
        src = src.replaceAll("0x1B", "0x1B 0x00");
        src = src.replaceAll("0x02", "0x1B 0xE7");
        src = src.replaceAll("0x03", "0x1B 0xE8");
        return src;
    }


    /**
     * 获取帧校验
     *
     * @param byteList 16进制的byte
     * @return
     */
    public static String getFrameCrc(byte[]... byteList) {
        /*int lengthByte = 0;
        for (int i = 0; i < byteList.length; i++) {
            lengthByte += (byteList[i] == null ? 0 : byteList[i].length);
        }
        byte[] allByte = new byte[lengthByte];
        int countLength = 0;
        for (int i = 0; i < byteList.length; i++) {
            byte[] b = byteList[i];
            if (null == b) {
                continue;
            }
            System.arraycopy(b, 0, allByte, countLength, b.length);
            countLength += b.length;
        }
        int crc = HexHelp.calCRC(allByte);
        String crcHighLow = HexHelp.getHighLow(crc);

        //转义特殊字符
        crcHighLow = getTranInfo(crcHighLow);
        return crcHighLow;*/

        int crc = HexHelp.calCRC(byteList[0]);
        String crcHighLow = HexHelp.getHighLow(crc);

        //转义特殊字符
        //crcHighLow = getTranInfo(crcHighLow);
        return crcHighLow;
    }

    /**
     * 获取文件的16进制内容分片
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static List<String> getFrameFileBody(File file) throws Exception {

        List<String> fileFrame = new ArrayList<>();

        //文件名
        String fileName = file.getName();
        String fileNameHex = HexHelp.strToHex(fileName);

        String splitHex = "2B";

        //文件流默认是10进制byte ,转换为16进制表示
        InputStream inputStream = new FileInputStream(file);
        int tempByte;
        List<String> hexList = new ArrayList<>();
        while ((tempByte = inputStream.read()) != -1) {
            String hex = Integer.toHexString(tempByte);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            hexList.add(hex);
        }

        /**
         * 上载文件超过2048,需要把文件分割2048字节若干段,
         * 正好是2048整数倍,最后也需要发送文件内容为0的字节
         */
        List<List<String>> metaBody = new ArrayList<>();
        for (int i = 0; i < hexList.size(); i += 2048) {
            if (i != 0 && i % 2048 == 0) {
                List<String> meta = hexList.subList(i, 2048 + i);
                metaBody.add(meta);
            }
        }
        if (metaBody.size() * 2048 <= hexList.size()) {
            List<String> finalMeta = hexList.subList(metaBody.size() * 2048, hexList.size());
            metaBody.add(finalMeta);
        }

        /**
         * 对文件内容分段组帧(文件名 + 分割符 + 指针偏移 + 内容)
         * 指针偏移按照高低位 先高后低
         */

        for (int i = 0; i < metaBody.size(); i++) {
            String pointer = Integer.toHexString(i * 2048);
            String pointerHighLow = HexHelp.getHighLow(Integer.parseInt(pointer));
            String bodyHex = StringUtils.join(metaBody.get(i),StringUtils.SPACE);

            String fileFrameMeta = StringUtils.join(
                    fileNameHex, StringUtils.SPACE,
                    splitHex, StringUtils.SPACE,
                    pointerHighLow, StringUtils.SPACE,
                    bodyHex
            );
            fileFrame.add(fileFrameMeta);
        }
        return fileFrame;
    }


    /**
     * 获取发送字符串
     * 帧头 + 地址 + 帧类型 + 帧数据 + 帧校验 + 帧尾
     *
     * @param frameAddress
     * @param frameType
     * @param frameBody
     * @return
     */
    public static String getSendMessageStr(String frameType, String frameBody) {

        byte[] fileBodyByte = HexHelp.hexStringToBytes(frameBody);
        String crc16;
        if (null == fileBodyByte) {
            crc16 = getFrameCrc(frameInfo.get(FRAME_ADDRESS), frameInfo.get(frameType));
        } else {
            crc16 = getFrameCrc(frameInfo.get(FRAME_ADDRESS), frameInfo.get(frameType), fileBodyByte);
        }


        //发送时body需要转义
        if (null != frameBody) {
            frameBody = getTranInfo(frameBody);
        }
        String sendMessage = StringUtils.join(
                FRAME_HEADER, StringUtils.SPACE,
                FRAME_ADDRESS, StringUtils.SPACE,
                frameType, StringUtils.SPACE,
                frameBody, StringUtils.SPACE,
                crc16, StringUtils.SPACE,
                FRAME_FOOTER
        );

        sendMessage = sendMessage.replaceAll("  ", " ");
        return sendMessage;
    }


    public static void main(String[] args) throws Exception {


        //获取CRC
        byte[] aa = {0x00, 0x00, 0x30, 0x36};
        System.out.println(getFrameCrc(aa));

        //String msgHex = "02 00 00 30 36 52 00 03";


    }


}
