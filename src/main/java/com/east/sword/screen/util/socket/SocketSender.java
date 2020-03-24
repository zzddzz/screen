package com.east.sword.screen.util.socket;

import com.east.sword.screen.util.HexHelp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @CreateDate 9:33 2020/3/20.
 * @Author ZZD
 */
public class SocketSender {

    private static SocketSender instance = new SocketSender();

    private SocketSender(){}

    public static SocketSender getInstance() {
        return instance;
    }


    /**
     * socket 发送消息
     * @param host
     * @param port
     * @param msg
     * @return
     * @throws Exception
     */
    public synchronized String sendMessage(String host,int port,byte msg[]) throws Exception {

        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        outputStream.write(msg);

        StringBuffer content = new StringBuffer();
        byte[] cacheByte = new byte[1024*5];//最长返回值限制
        while (true){
            inputStream.read(cacheByte);
            String metaChar = HexHelp.bytesToHex(cacheByte);
            content.append(metaChar);
            break;
        }
        String response = content.toString();
        return response.substring(0,response.indexOf("030000")+2);
    }



}
