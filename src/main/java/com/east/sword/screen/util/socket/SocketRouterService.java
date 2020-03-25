package com.east.sword.screen.util.socket;

import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.util.HexHelp;
import com.east.sword.screen.vo.Jxo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Socket 发送类
 *
 * @CreateDate 8:29 2020/3/25.
 * @Author ZZD
 */
@Slf4j
@Component
public class SocketRouterService {

    /**
     * 发送消息
     * @param resource
     * @param screen
     * @return
     * @throws Exception
     */
    public void sendMessage(Resource resource, Screen screen) throws Exception {

        //发送文字
        if (Resource.TYPE_FONT.equals(resource.getType())) {

            //写入模板
            File directory = new File(".");
            String fileName = "play" + resource.getNo() + ".lst";
            File sendTemplete = new File(directory.getAbsolutePath() + File.separator + fileName);
            File srcFile = ResourceUtils.getFile("classpath:play.lst");
            FileUtils.copyFile(srcFile, sendTemplete);
            FileWriter fw = new FileWriter(sendTemplete);
            fw.write(resource.getContent());
            fw.close();

            //发送操作
            List<String> frameBody = Jxo.getFrameFileBody(sendTemplete);
            for (String metaBody : frameBody) {
                String msgHex = Jxo.getNeedMsgFormat(Jxo.FRAME_UPLOAD_FILE,metaBody);
                byte[] msgByte = HexHelp.hexStrToBinary(msgHex);
                String msg = SocketSender.getInstance().sendMessage(screen.getHost(),screen.getPort(),msgByte);
                log.info("socket 返回值信息Screen no: {},Resource id: {},response: {}",screen.getNo(),resource.getId(),msg);
            }
        }
    }
}
