package com.east.sword.screen.util.socket;

import com.east.sword.screen.config.ConstantConfig;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.util.HexHelp;
import com.east.sword.screen.vo.Jxo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
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

    @Autowired
    private ConstantConfig constantConfig;

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
            //String fileName = "play" + resource.getNo() + ".lst";
            String fileName = "play.lst";
            File sendTemplete = new File(constantConfig.fileCache  + fileName);
            File srcFile = ResourceUtils.getFile("classpath:play.lst");
            FileUtils.copyFile(srcFile, sendTemplete);
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(sendTemplete,true),"GBK"));
            writer.write(resource.getContent());
            writer.close();

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
