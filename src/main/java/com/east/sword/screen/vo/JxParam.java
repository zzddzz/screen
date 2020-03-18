package com.east.sword.screen.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 金晓命令格式
 *
 * @CreateDate 10:52 2020/3/18.
 * @Author ZZD
 */
@Data
public class JxParam {

    /**
     * 取当前故障
     */
    private static final String JX_ERROR = "01";

    /**
     * 上传文件
     */
    private static final String JX_UPLOAD = "10";

    /**
     * 下载文件
     */
    private static final String JX_DOWNLOAD = "09";

    /**
     * 显示预置播放列表内容
     */
    private static final String JX_ACTIVE_PLAY = "98";

    /**
     * 取当前显示内容
     */
    private static final String JX_GET_PLAY = "97";

    /**
     * 设置亮度方式/调节亮度
     */
    private static final String JX_ACTIVE_LIGHT = "03";

    /**
     * 取亮度方式/显示亮度
     */
    private static final String JX_GET_LIGHT = "98";


    /**
     * 帧头
     */
    private static final String frameHeader = "0x02";

    /**
     * 地址
     */
    private static final String frameAddress = "0x000x00";

    /**
     * 帧类型(指令)
     * 取当前故障           (01 0x30 0x31)
     * 上传文件             (10 0x31 0x30)
     * 下载文件             (09 0x30 0x39)
     * 显示预置播放列表       (98 0x39 0x38)
     * 取当前显示内容         (97 0x39 0x37)
     * 设置亮度方式/调节亮度   (03 0x30 0x33)
     * 取亮度方式/显示亮度     (06 0x30 0x36)
     */
    private static String frameType[] = {"01", "0x300x31", "10", "0x310x30", "09", "0x300x39", "98", "0x390x38",
            "97", "0x390x37", "03", "0x300x33", "06", "0x300x36"};

    /**
     * 帧数据
     */
    private String frameData;

    /**
     * 帧校验
     */
    private String frameCrc;

    /**
     * 帧尾
     */
    private static final String frameBar = "0x03";


    /**
     * 获取请求命令
     *
     * @param orderType
     * @return
     */
    public static String getJxRequest(String orderType, String data) {
        int orderPos = Arrays.asList(frameType).indexOf(orderType);
        String typeOrder = frameType[orderPos + 1];

        int frameCrc = getCrc(frameAddress, typeOrder, data);
        return StringUtils.join(frameHeader, frameAddress, typeOrder, data, frameCrc, frameBar);
    }

    /**
     * 获取加密CRC 帧校验
     *
     * @param frameAddress
     * @param orderType
     * @param data
     */
    public static int getCrc(String frameAddress, String typeOrder, String data) {
        String finalStr = StringUtils.join(frameAddress, typeOrder, data);
        byte[] dataFrame = finalStr.getBytes();
        int crc = calCRC(dataFrame);
        return crc;

    }

    static int[] fcstab = {
            0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50A5, 0x60C6, 0x70E7,
            0x8108, 0x9129, 0xA14A, 0xB16B, 0xC18C, 0xD1AD, 0xE1CE, 0xF1EF,
            0x1231, 0x0210, 0x3273, 0x2252, 0x52B5, 0x4294, 0x72F7, 0x62D6,
            0x9339, 0x8318, 0xB37B, 0xA35A, 0xD3BD, 0xC39C, 0xF3FF, 0xE3DE,
            0x2462, 0x3443, 0x0420, 0x1401, 0x64E6, 0x74C7, 0x44A4, 0x5485,
            0xA56A, 0xB54B, 0x8528, 0x9509, 0xE5EE, 0xF5CF, 0xC5AC, 0xD58D,
            0x3653, 0x2672, 0x1611, 0x0630, 0x76D7, 0x66F6, 0x5695, 0x46B4,
            0xB75B, 0xA77A, 0x9719, 0x8738, 0xF7DF, 0xE7FE, 0xD79D, 0xC7BC,
            0x48C4, 0x58E5, 0x6886, 0x78A7, 0x0840, 0x1861, 0x2802, 0x3823,
            0xC9CC, 0xD9ED, 0xE98E, 0xF9AF, 0x8948, 0x9969, 0xA90A, 0xB92B,
            0x5AF5, 0x4AD4, 0x7AB7, 0x6A96, 0x1A71, 0x0A50, 0x3A33, 0x2A12,
            0xDBFD, 0xCBDC, 0xFBBF, 0xEB9E, 0x9B79, 0x8B58, 0xBB3B, 0xAB1A,
            0x6CA6, 0x7C87, 0x4CE4, 0x5CC5, 0x2C22, 0x3C03, 0x0C60, 0x1C41,
            0xEDAE, 0xFD8F, 0xCDEC, 0xDDCD, 0xAD2A, 0xBD0B, 0x8D68, 0x9D49,
            0x7E97, 0x6EB6, 0x5ED5, 0x4EF4, 0x3E13, 0x2E32, 0x1E51, 0x0E70,
            0xFF9F, 0xEFBE, 0xDFDD, 0xCFFC, 0xBF1B, 0xAF3A, 0x9F59, 0x8F78,
            0x9188, 0x81A9, 0xB1CA, 0xA1EB, 0xD10C, 0xC12D, 0xF14E, 0xE16F,
            0x1080, 0x00A1, 0x30C2, 0x20E3, 0x5004, 0x4025, 0x7046, 0x6067,
            0x83B9, 0x9398, 0xA3FB, 0xB3DA, 0xC33D, 0xD31C, 0xE37F, 0xF35E,
            0x02B1, 0x1290, 0x22F3, 0x32D2, 0x4235, 0x5214, 0x6277, 0x7256,
            0xB5EA, 0xA5CB, 0x95A8, 0x8589, 0xF56E, 0xE54F, 0xD52C, 0xC50D,
            0x34E2, 0x24C3, 0x14A0, 0x0481, 0x7466, 0x6447, 0x5424, 0x4405,
            0xA7DB, 0xB7FA, 0x8799, 0x97B8, 0xE75F, 0xF77E, 0xC71D, 0xD73C,
            0x26D3, 0x36F2, 0x0691, 0x16B0, 0x6657, 0x7676, 0x4615, 0x5634,
            0xD94C, 0xC96D, 0xF90E, 0xE92F, 0x99C8, 0x89E9, 0xB98A, 0xA9AB,
            0x5844, 0x4865, 0x7806, 0x6827, 0x18C0, 0x08E1, 0x3882, 0x28A3,
            0xCB7D, 0xDB5C, 0xEB3F, 0xFB1E, 0x8BF9, 0x9BD8, 0xABBB, 0xBB9A,
            0x4A75, 0x5A54, 0x6A37, 0x7A16, 0x0AF1, 0x1AD0, 0x2AB3, 0x3A92,
            0xFD2E, 0xED0F, 0xDD6C, 0xCD4D, 0xBDAA, 0xAD8B, 0x9DE8, 0x8DC9,
            0x7C26, 0x6C07, 0x5C64, 0x4C45, 0x3CA2, 0x2C83, 0x1CE0, 0x0CC1,
            0xEF1F, 0xFF3E, 0xCF5D, 0xDF7C, 0xAF9B, 0xBFBA, 0x8FD9, 0x9FF8,
            0x6E17, 0x7E36, 0x4E55, 0x5E74, 0x2E93, 0x3EB2, 0x0ED1, 0x1EF0
    };


    public static int calCRC(byte[] dataFrame) {
        int fcs = 0xffff;
        for (int i = 0, j = dataFrame.length; i < j; i++) {
            fcs = ((fcs >> 8) ^ fcstab[(fcs ^ dataFrame[i]) & 0xff]);
        }
        return fcs;
    }

    public static void main(String[] args) {

        String frameHeader = "0x02";//帧头
        String frameAddress = "0x000x00";//默认地址
        String frameType = "0x390x37";//取当前显示内容命令

        //获取CRC
        String crcStr = StringUtils.join(frameAddress, frameType, null);
        byte[] dataFrame = crcStr.getBytes();
        int crc = calCRC(dataFrame);

        System.out.println(crc);
        String finalSendStr = StringUtils.join(frameHeader, frameAddress, frameType, null, crc, frameBar);
        System.out.println(finalSendStr);
    }


    /**
     * 帧数据,帧校验 字符特殊处理
     *
     * @param src
     * @return
     */
    public String specialEncode(String src) {
        src = src.replaceAll("0x1B", "0x1B0x00");
        src = src.replaceAll("0x02", "0x1B0xE7");
        src = src.replaceAll("0x03", "0x1B0xE8");
        return src;
    }

    /**
     * 帧数据解码处理
     *
     * @param src
     * @return
     */
    public String specialDecode(String src) {
        src = src.replaceAll("0x1B0x00", "0x1B");
        src = src.replaceAll("0x1B0xE7", "0x02");
        src = src.replaceAll("0x1B0xE8", "0x03");
        return src;
    }

    /**
     * 字符串转16进制
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = "0x" + Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 16进制转2进制数据
     *
     * @param str
     * @return
     */
    public static String strToByteFrom16(String str) {
        byte res = (byte) Integer.parseInt(str, 16);
        String code = Integer.toBinaryString((res & 0xFF) + 0x100);
        return code;
    }


}
