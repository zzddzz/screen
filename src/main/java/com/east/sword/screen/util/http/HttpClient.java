package com.east.sword.screen.util.http;

import com.east.sword.screen.entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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

    public static MediaType TYPE_FORM = MediaType.APPLICATION_FORM_URLENCODED;
    public static MediaType TYPE_JSON = MediaType.APPLICATION_JSON;


    private static RestTemplate restTemplate ;

    public RestTemplate getRestTemplate() {

        if (null == restTemplate) {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(8000);
            requestFactory.setReadTimeout(10000);
            restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(requestFactory);
            return restTemplate;
        } else {
            return restTemplate;
        }
    }

    /**
     * Post 请求
     *
     * @param url
     * @param mediaType
     * @param multiValueMap
     * @return
     */
    public String httpPostJson(String url, String jsonCommond) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(jsonCommond, headers);
        ResponseEntity<String> response = getRestTemplate().postForEntity(url, request, String.class);
        return response.getBody();
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

        ResponseEntity<String> response = getRestTemplate().postForEntity(url, files, String.class);
        return response.getBody();
    }

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url) {
        ResponseEntity responseEntity = getRestTemplate().getForEntity(url, String.class);
        String info = responseEntity.getBody() == null ? StringUtils.EMPTY : responseEntity.getBody().toString();
        return info;
    }

    /**
     * Put 请求 (轮播)
     *
     * @param url
     */
    public void httpPut(String url,String jsonCommond) {
        if (StringUtils.isNotBlank(jsonCommond)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity request = new HttpEntity<>(jsonCommond, headers);
            getRestTemplate().put(url, request);
        } else {
            getRestTemplate().put(url, null);
        }

    }

    /**
     * Delete 请求
     *
     * @param url
     */
    public void httpDelete(String url) {
        getRestTemplate().delete(url);
    }


}
