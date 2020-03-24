package com.east.sword.screen.util.socket;

import com.east.sword.screen.vo.GoldMon;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @CreateDate 10:43 2020/3/18.
 * @Author ZZD
 */
@Slf4j
public class SocketTest {

    public static void main(String[] args) throws Exception {
        String host = "37.168.190.172";
        int port = 60001;



        //String msgHex = "02 00 00 39 37 f9 12 03";
        //0200003030303030373030303130303030365c433036343038
        //0200003030303030373030303130303030365c433036343038
        File file = new File("d:/play1.lst");
        List<String> frameBody = GoldMon.getFrameFileBody(file);

        //System.out.println(frameBody.size());
        String msgHex = GoldMon.getSendMessageStr(GoldMon.FRAME_UPLOAD_FILE,frameBody.get(0));
        byte[] msgByte = hexStrToBinaryStr(msgHex);
        String msg = SocketSender.getInstance().sendMessage(host,port,msgByte);
        System.out.println(msg);
       // byte[] response = {2, 0, 0, 49, 48, 48, -63, 83, 3};
    }

    public static byte[] hexStrToBinaryStr(String hexString) {
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
}
