package com.east.sword.screen.util.http;

import com.east.sword.screen.entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * http 客户端
 *
 * @CreateDate 22:40 2020/2/17.
 * @Author ZZD
 */
@Slf4j
@Component
public class HttpClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static MediaType TYPE_FORM = MediaType.APPLICATION_FORM_URLENCODED;
    public static MediaType TYPE_JSON = MediaType.APPLICATION_JSON;

    RestTemplate restTemplate = new RestTemplate();

    /**
     * Post 请求
     *
     * @param url
     * @param mediaType
     * @param multiValueMap
     * @return
     */
    public String httpPost(String url, MediaType mediaType, MultiValueMap multiValueMap) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(multiValueMap, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.error("request url error {} ,detail{}", url, e);
            return StringUtils.EMPTY;
        }
    }

    /**
     * post 上传文件
     *
     * @param url
     * @param uploadFilePath
     * @param fileName
     * @return
     */
    public String httpPostMedia(String url, Resource resource) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        //设置请求体，注意是LinkedMultiValueMap
        String filePath = resource.getFilePath();
        String vsnFileName = resource.getVsnName();
        String picFileName = resource.getFileName();
        FileSystemResource f1Resource = new FileSystemResource(filePath + vsnFileName);
        FileSystemResource f2Resource = new FileSystemResource(filePath + picFileName);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("f1", f1Resource);
        form.add("f2", f2Resource);

        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, files, String.class);
        return response.getBody();
    }

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url) {
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        String info = responseEntity.getBody() == null ? StringUtils.EMPTY : responseEntity.getBody().toString();
        return info;
    }

    /**
     * Put 请求 (轮播)
     *
     * @param url
     */
    public void httpPut(String url) {
        restTemplate.put(url, null);
    }

    /**
     * Delete 请求
     *
     * @param url
     */
    public void httpDelete(String url) {
        restTemplate.delete(url);
    }


}