package com.east.sword.screen.util.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * @CreateDate 10:43 2020/3/18.
 * @Author ZZD
 */
@Slf4j
public class SocketTest {

    public static void main(String[] args) throws IOException {
        String host = "37.168.190.172";
        int port = 60001;

        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String uuid = UUID.randomUUID().toString();
        log.info("uuid: {}", uuid);
        outputStream.write(uuid.getBytes());
        DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
        String content = "";
        while (true){
            byte[] buff = new byte[1024];
            inputStream.read(buff);
            String buffer = new String(buff, "utf-8");
            content += buffer;
            log.info("info: {}", buff);
            File file = new File("json.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
        }
    }
}
