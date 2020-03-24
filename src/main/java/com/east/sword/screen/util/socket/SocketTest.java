package com.east.sword.screen.util.socket;

import com.east.sword.screen.util.HexHelp;
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

        byte[] bb = {0x00, 0x00, 0x39, 0x37};
        int cc = HexHelp.calCRC(bb);
        System.out.println("校验crc正确性:63929---" + cc);


        //String msgHex = "02 00 00 39 37 f9 12 03";
        //0200003030303030373030303130303030365c433036343038
        //0200003030303030373030303130303030365c433036343038
        File file = new File("d:/play1.lst");
        List<String> frameBodyList = GoldMon.getFrameFileBodyOrigin(file);

        for(int i =0 ;i < frameBodyList.size(); i++) {
            String frameBody = frameBodyList.get(i);
            String msgHex = GoldMon.getSendMessageStr(file.getName(),GoldMon.FRAME_UPLOAD_FILE, frameBody,i);
            System.out.println("完整帧：" + msgHex);
            byte[] msgByte = HexHelp.hexStringToBytes(msgHex);
            String msg = SocketSender.getInstance().sendMessage(host, port, msgByte);
            System.out.println("返回值："+msg);
        }

        //System.out.println(frameBody.size());

        // byte[] response = {2, 0, 0, 49, 48, 48, -63, 83, 3};
    }

}
