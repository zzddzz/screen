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

        String content = "";
        while (true){
            byte[] buff = new byte[3072];
            inputStream.read(buff);
            content = HexHelp.bytesToHex(buff);
            content = content.substring(0,50);
            break;
        }
        return content;
    }



}
