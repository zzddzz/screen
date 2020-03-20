package com.east.sword.screen.util.socket;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * @CreateDate 9:33 2020/3/20.
 * @Author ZZD
 */
public class SocketSender {

    private static SocketSender instance = new SocketSender();

    private SocketSender(){

    }

    public static SocketSender getInstance() {
        return instance;
    }

    public void sendMessage(Socket socket,String msg) {
        PrintWriter out;
        System.out.println(msg);
        try {
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
