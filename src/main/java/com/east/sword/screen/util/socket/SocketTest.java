package com.east.sword.screen.util.socket;

import com.east.sword.screen.util.HexHelp;
import com.east.sword.screen.vo.Jxo;
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
        File file = new File("d:/play.lst");
        List<String> frameBody = Jxo.getFrameFileBody(file);

        for (String metaBody : frameBody) {
            String msgHex = Jxo.getNeedMsgFormat(Jxo.FRAME_UPLOAD_FILE,metaBody);
            byte[] msgByte = HexHelp.hexStrToBinary(msgHex);
            String msg = SocketSender.getInstance().sendMessage(host,port,msgByte);
            System.out.println(msg);
        }

    }



}
