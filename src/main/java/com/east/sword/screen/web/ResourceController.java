package com.east.sword.screen.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.east.sword.screen.config.ConstantConfig;
import com.east.sword.screen.entity.Resource;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.msg.IMsgService;
import com.east.sword.screen.service.IResourceService;
import com.east.sword.screen.service.IScreenService;
import com.east.sword.screen.util.FileUtil;
import com.east.sword.screen.util.WaterMarkUtils;
import com.east.sword.screen.vo.VsnJson;
import com.east.sword.screen.web.dto.PageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Resource controller
 *
 * @author ZZD
 * @since 2020-02-20
 */
@Slf4j
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    private static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IScreenService screenService;

    @Autowired
    private ConstantConfig constantConfig;

    @Qualifier("routerMsgService")
    @Autowired
    private IMsgService msgService;

    /**
     * 加载资源首页
     *
     * @return
     */
    @GetMapping("/index")
    public String loadIndex(String no, String msg, Model model) {
        model.addAttribute("no", no);
        model.addAttribute("msg", msg);
        return "resource";
    }

    /**
     * 插播管理
     * @param model
     * @return
     */
    @GetMapping("/inter-index")
    public String loadInterIndex(Model model) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("enable",Screen.STATUS_ENABLE);
        List<Screen> screenList = screenService.selectList(entityWrapper);
        model.addAttribute("screenList",screenList);
        return "inter";
    }

    @ResponseBody
    @RequestMapping("/page")
    public Map screenPageList(PageHelper<Resource> pageHelper,Resource resource ) {
        try {
            Map data = new TreeMap();
            Page page = pageHelper.getPage();
            List<Resource>  records = resourceService.selectResourcePage(page,resource);
            data.put("data", records);
            data.put("recordsTotal", page.getTotal());
            data.put("recordsFiltered", page.getTotal());
            return data;
        } catch (Exception e) {
            log.error("screen list error:{}", e);
            return null;
        }
    }

    /**
     * 更改资源状态,是否同步到大屏上的资源
     *
     * @param resource
     * @return
     */
    @ResponseBody
    @RequestMapping("/change")
    public String changeResourceStatus(int resourceId, String enable) {
        try {

            Resource resource = resourceService.selectById(resourceId);
            Screen screen = screenService.selectById(resource.getNo());


            //上传大屏,判断大屏资源是否占满
            if (Resource.UNABLE.equals(resource.getEnable())) {//上传大屏资源

                //只有FTP同步的图片-->校验大屏设置的图片数量限制,自定义的不限制
                if (Resource.TYPE_SYNC.equals(resource.getSrcType())) {
                    // 判断大屏播放数量是否占满
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("enable", "1");
                    entityWrapper.eq("no", resource.getNo());
                    entityWrapper.eq("type",Resource.TYPE_PIC);
                    int enableSize = resourceService.selectList(entityWrapper).size();

                    if (enableSize >= screen.getPlayPicNum()) {
                        return String.valueOf(screen.getPlayPicNum());
                    }
                }
                msgService.putResource(screen, resource);
            } else {//下架资源
                msgService.putDownResource(screen, resource);
            }

            resource.setEnable(enable);
            resourceService.updateById(resource);
            return SUCCESS;
        } catch (Exception e) {
            log.error("change error : {}", e);
            return FAIL;
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String deleteResource(Resource resource) {
        try {
            resource = resourceService.selectById(resource.getId());
            resourceService.deleteById(resource.getId());

            //删除大屏资源
            Screen screen = screenService.selectById(resource.getNo());
            msgService.delResource(screen, resource);
            return SUCCESS;
        } catch (Exception e) {
            log.error("delete resource error : {}", e);
            return FAIL;
        }
    }

    @RequestMapping("/showPic")
    public void showPicture(String id, HttpServletResponse response) {
        try {
            Resource resource = resourceService.selectById(id);
            String filePath = constantConfig.fileCache + resource.getFileName();
            InputStream in = new FileInputStream(new File(filePath));

            FileCopyUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            log.error("查看图片异常:{}", e);
        }
    }

    /**
     * 合成图片
     *
     * @return
     */
    @RequestMapping("/compPic")
    public RedirectView saveResourcePic(@RequestParam("backGround") MultipartFile backGround,
                                        @RequestParam("content") String content,
                                        @RequestParam("no") Integer no,
                                        @RequestParam("size") Integer size,
                                        RedirectAttributes redirectAttributes) {
        RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        redirectTarget.setUrl("inter-index");

        try {
            String originName = backGround.getOriginalFilename();

            String todayStamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
            String prifixName = no + "_" + todayStamp + "_font";
            String suffixName = originName.substring(originName.lastIndexOf("."), originName.length());
            String fileName = prifixName + suffixName;
            String vsnName = prifixName + ".vsn";

            Resource resource = new Resource();
            resource.setEnable(Resource.UNABLE);//默认不可用
            resource.setDelFlag(Resource.UNDEL);
            resource.setType(Resource.TYPE_PIC);
            resource.setFileName(fileName);
            resource.setVsnName(vsnName);
            resource.setResourceDateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            resource.setOriginName(originName);
            resource.setFilePath(constantConfig.fileCache);
            resource.setCreateDate(new Date());
            resource.setNo(no);
            resource.setSrcType(Resource.TYPE_CUT);

            resourceService.insert(resource);

            File srcBackFile = FileUtil.multipartFileToFile(backGround);
            String targetFilePicName = resource.getFilePath() + resource.getFileName();
            WaterMarkUtils.createWaterMark(srcBackFile, targetFilePicName, content,size);

            JsonObject vsnJson = VsnJson.getVsn(VsnJson.VSN_PIC, resource.getVsnName(), resource.getFileName(), targetFilePicName);
            String vsnContent = gson.toJson(vsnJson);
            FileUtil.createFile(resource.getFilePath(), resource.getVsnName(), vsnContent);

            redirectTarget.addStaticAttribute("no", no);
            redirectTarget.addStaticAttribute("msg", SUCCESS);
        } catch (Exception e) {
            log.error("合成图片异常:{}", e);
            redirectTarget.addStaticAttribute("no", no);
            redirectTarget.addStaticAttribute("msg", FAIL);
        }
        return redirectTarget;
    }

    /**
     * 生成文字播放信息
     * 根据类型判断,金晓的直接生成Resource  卡莱特的需要生成vsn 文件
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveFont")
    public String saveResourcePic(@RequestParam("content") String content,
                                        @RequestParam("no") Integer no,
                                        @RequestParam("size") Integer size) {
        try {
            Screen screen = screenService.selectById(no);
            Resource resource = new Resource();
            resource.setEnable(Resource.UNABLE);//默认不可用
            resource.setDelFlag(Resource.UNDEL);
            resource.setType(Resource.TYPE_FONT);
            resource.setResourceDateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            resource.setFilePath(constantConfig.fileCache);
            resource.setCreateDate(new Date());
            resource.setNo(no);
            resource.setSrcType(Resource.TYPE_CUT);
            resource.setContent(content);


            //卡莱特生成VSN
            if (Screen.TYPE_KLT.equals(screen.getType())) {
                String fileName = no + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")+".vsn";
                resource.setOriginName(fileName);
                resource.setVsnName(fileName);
                resource.setFileName(fileName);

                File vsnFontFile = new File(constantConfig.fileCache + File.separator + fileName);
                if (!vsnFontFile.exists()) {
                    vsnFontFile.createNewFile();
                }
                File srcFile = ResourceUtils.getFile("classpath:klt_font.json");

                String setContent = getFileInfo(srcFile);
                setContent = setContent.replace("@font@",content);
                FileWriter fw = new FileWriter(vsnFontFile);
                fw.write(setContent);
                fw.close();
            }
            if (Screen.TYPE_JX.equals(screen.getType())) {
                String fileName = no + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")+".lst";
                resource.setOriginName(fileName);
                resource.setVsnName(fileName);
                resource.setFileName(fileName);
            }
            resourceService.insert(resource);

            return SUCCESS;
        } catch (Exception e) {
            log.error("saveFont error :{}",e);
            return FAIL;
        }
    }

    public String getFileInfo (File file) throws Exception {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        reader = new BufferedReader(new FileReader(file));
        String tempStr;
        while ((tempStr = reader.readLine()) != null) {
            sbf.append(tempStr);
        }
        reader.close();
        return sbf.toString();
    }



}

