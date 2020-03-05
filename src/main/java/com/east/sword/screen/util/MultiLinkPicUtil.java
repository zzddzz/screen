package com.east.sword.screen.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 多行文本生成图片工具
 *
 * @CreateDate 8:38 2020/2/20.
 * @Author ZZD
 */
@Slf4j
public class MultiLinkPicUtil {

    private static Font fontTemplete = new Font("宋体", Font.PLAIN, 22);

    /**
     * 每行或每个文字的高度
     */
    private static int lineHeight = 30;

    private static String windowsRtnLine = "\n";

    /**
     * 根据str,font的样式等生成图片
     *File srcImgFile, String tarImgPath, String waterMarkContent,int size
     * @param strArr
     * @param font
     * @param width
     * @param image_height
     * @throws Exception
     */
    public static void createImage(File srcImgFile,String tarImgPath,String text,int size) throws Exception {

        Font font = new Font("微软雅黑", Font.BOLD, size);

        //文本按照段落切分
        String[] sectionArr = text.split(windowsRtnLine);

        // 读取原图片信息,获取图片宽、高
        Image srcImg = ImageIO.read(srcImgFile);
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);

        // 每张图片有多少行文字,预留两行空间
        int pageLine = srcImgHeight / lineHeight - 2;

        //单个文字的宽度，计算图片每行容纳字数
        FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
        int stringWidth = fm.charWidth('字');// 标点符号也算一个字
        int line_string_num = srcImgWidth % stringWidth == 0 ? (srcImgWidth / stringWidth) : (srcImgWidth / stringWidth) + 1;
        log.info("每行容纳文字数:{}",line_string_num);

        //将每行显示的文章封装
        java.util.List<String> listSrcStr = new ArrayList<>();
        listSrcStr.addAll(Arrays.asList(sectionArr));
        ArrayList<String> newList = new ArrayList();
        for (int j = 0; j < listSrcStr.size(); j++) {
            if (listSrcStr.get(j).length() > line_string_num) {
                newList.add(listSrcStr.get(j).substring(0, line_string_num));
                listSrcStr.add(j + 1, listSrcStr.get(j).substring(line_string_num));
                listSrcStr.set(j, listSrcStr.get(j).substring(0, line_string_num));
            } else {
                newList.add(listSrcStr.get(j));
            }
        }

        //计算需要几张图片显示文字
        int a = newList.size();
        int b = pageLine;
        int imgNum = a % b == 0 ? (a / b) : (a / b) + 1;
        for (int m = 0; m < imgNum; m++) {
            //String filePath = "F:\\" + m + ".png";
            File outFile = new File(tarImgPath);

            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(Color.blue); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            // 每张多少行，当到最后一张时判断是否填充满
            for (int i = 0; i < pageLine; i++) {
                int index = i + m * pageLine;
                if (newList.size() - 1 >= index) {

                    //i + 2,上方空一行
                    g.drawString(newList.get(index), 0, lineHeight * (i + 2));
                }
            }
            g.dispose();
            ImageIO.write(bufImg, "png", outFile);// 输出png图片
        }
    }

    public static void main(String[] args) throws Exception {
        String text = "“  两国交兵，不斩来使”在后世流传下来的交战规则主要只有“两国交兵，不斩来使”。\n" +
                "春秋时期诸侯派出的外交使节是不可侵犯的。公元前596年楚国派出申舟出使齐国，楚庄王特意嘱咐不要从宋国经过。\n" +
                "宋国执政华元听说了，觉得这是对宋国的莫大侮辱，就设伏击杀死楚国使者。\n" +
                "楚庄王为此“投袂而起”，出动大军包围宋国国都整整9个月。宋国派出使者到晋国告急，晋国上一年刚被楚军打败，\n" +
                "不敢冒与楚国全面冲突的危险，只是派解扬为使者劝宋国坚守，不要投降。解扬经过郑国，被郑国抓起来交给楚国。\n" +
                "楚庄王亲自接见解扬，企图买通他，要他向宋军喊话，说晋军不再提供救援，断绝宋军的希望，解扬不同意。经楚庄王几次威逼利诱，解扬才答应下来。可是当解扬来到了望城中的楼车上，就大声疾呼，说晋国援军不日就到，请宋国无论如何要坚持下去。楚庄王大怒，解扬说：“我答应你的条件只是为了实现使命，现在使命实现了，请立刻处死我。”楚庄王无话可说，反而释放他回晋国。长期围困而无战果，楚庄王打算退兵，可申舟的父亲拦在车前，说：“我儿子不惜生命以完成国王的使命，难道国王要食言了吗？”楚庄王无言以对。申舟父亲建议在宋国建造住房、耕种土地，表示要长期占领宋国，宋国就会表示屈服。宋国见楚军不肯撤退，就派华元为使者来谈判。华元半夜里潜入楚军大营，劫持了楚军统帅子反，说：“我的国君要我为使者来谈判，现在城内确实已是‘易子而食，析骸以爨’，但是如果订立城下之盟则情愿举国牺牲。贵军退到三十里外，我国唯命是听。”子反就在睡床上保证做到。第二天报告了楚庄王，楚军真的退30里外，和宋国停战，双方保证不再互相欺瞒，华元作为这项和约的人质到楚国居住。\n" +
                "后世将这一交战规则称之为“两国交兵，不斩来使”。历史上最著名的战时两国使节以礼相见的故事是“彭城相会”。450年南朝刘宋与北魏发生战争，刘宋发起北伐，先胜后败，战略据点彭城被包围。江夏王刘义恭率领军队死守彭城（今徐州），北魏太武帝想一举打过长江，派出李孝伯为使节进彭城劝降。刘义恭派了张畅为代表与李孝伯谈判。两人都是当时的“名士”，互相代表各自的君主赠送礼品，尽管处在极其残酷的战争环境，但他们在谈判中却仍然是文质彬彬、礼貌周全。这次谈判本身并没有什么实质性的结果，可双方的礼节及言辞，一直被后世誉为战场佳话。";

        //createImage(text, "F://background.png");

    }

}
