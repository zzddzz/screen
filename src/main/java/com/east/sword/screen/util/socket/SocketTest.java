package com.east.sword.screen.util.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @CreateDate 10:43 2020/3/18.
 * @Author ZZD
 */
@Slf4j
public class SocketTest {

    public static void main(String[] args) throws IOException {
        /*String host = "37.168.190.172";
        int port = 60001;

        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        //byte[] msg = {0x02,0x00,0x00,0x39,0x37,0xf9,0xb9,0x03};
        String msgHex = "02 00 00 30 36 53 00 03";
        //byte[] msg = {0x02,0x00,0x00,0x39,0x37,0x03};

        byte[] msg = hexStrToBinaryStr(msgHex);
        System.out.println(msg);
        outputStream.write(msg);
        String content = "";
        while (true){
            byte[] buff = new byte[1024];
            inputStream.read(buff);
            String buffer = new String(buff, "utf-8");
            content += buffer;
            log.info("info: {}", buff);
            File file = new File("D:/json.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
        }*/

        String msgHex = "02 00 00 30 36 53 00 03";
        byte[] msg = hexStrToBinaryStr(msgHex);
        System.out.println(msg);

        byte[] response = {2, 0, 0, 49, 48, 48, -63, 83, 3};
        System.out.println(bytesToHex(response));
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
