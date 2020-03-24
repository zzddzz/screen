package com.east.sword.screen.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @CreateDate 14:06 2020/3/23.
 * @Author ZZD
 */
public class HexHelp {

    /**
     * 二进制转16进制
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * 字符串转16进制
     * @param str
     * @return
     */
    public static String strToHex(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            sb.append(Integer.toString(c, 16)).append(StringUtils.SPACE);
        }
        return sb.toString().substring(0,sb.toString().length() - 1);
    }

    /**
     * 16字符串进制转byte[]
     * @param hexString
     * @return
     */
    public static byte[] hexStrToBinary(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len / 2];
        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index/2] = (byte)Integer.parseInt(sub,16);
            index += 2;
        }
        return bytes;
    }

    /**
     * 获取高低位(先高后低)
     * @param src
     * @return
     */
    public static String getHighLow(int src,int length) {
        String big = Integer.toHexString(src / 256);
        String little = Integer.toHexString(src % 256);

        if (big.length() < 2) {
            big = "0" + big;
        }
        if (little.length() < 2) {
            little = "0" + little;
        }
        int compLength = length*2 - big.length() - little.length();
        String[] compArray = new String[compLength];
        for(int i = 0; i < compLength;i++) {
            compArray[i] = "0";
        }
        String compBits =  StringUtils.join(compArray);

        String finalStr = StringUtils.join(compBits,big,little);
        String[] rtn = new String[finalStr.length()/2];
        int idx = 0;
        for (int i = 0;i < finalStr.length();i+=2){
            rtn[idx] = finalStr.substring(i,i + 2);
            idx++;
        }
        return StringUtils.join(rtn,StringUtils.SPACE);
    }

    /**
     * CRC16 算法
     * @param data_arr
     * @return
     */
    public static char calCRC(byte[] data_arr) {
        char crc16 = 0;
        int i;
        int data_len = data_arr.length;
        for (i = 0; i < (data_len); i++) {
            crc16 = (char) ((crc16 >> 8) | (crc16 << 8));
            crc16 ^= data_arr[i] & 0xFF;
            crc16 ^= (char) ((crc16 & 0xFF) >> 4);
            crc16 ^= (char) ((crc16 << 8) << 4);
            crc16 ^= (char) (((crc16 & 0xFF) << 4) << 1);
        }
        return crc16;
    }


}
