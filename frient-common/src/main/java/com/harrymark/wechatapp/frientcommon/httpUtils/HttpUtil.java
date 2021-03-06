package com.harrymark.wechatapp.frientcommon.httpUtils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Http请求工具类
 * Created by haoweima on 2019/3/12.
 */
@Component
@SuppressWarnings("all")
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    @Autowired
    @Qualifier("httpRestTemplate")
    private RestTemplate httpRestTemplate;

    @Autowired
    @Qualifier("httpsRestTemplate")
    private RestTemplate httpsRestTemplate;

    @Autowired
    private Gson gson;

    /**
     * post请求api cloud接口
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T postApiObject(String url, Class<T> clazz, Object object) {
        logger.info("请求url:" + url + ",入参:" + gson.toJson(object));
        ResponseEntity<String> responseEntity = httpRestTemplate.postForEntity(url, object, String.class);
        T response = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("请求" + url + "成功,状态:" + responseEntity.getStatusCodeValue() + ",响应body:" + responseEntity.getBody());
            if ("".equals(responseEntity.getBody())) {
                return null;
            }
            try {
                response = gson.fromJson(responseEntity.getBody(), clazz);
            } catch (Exception e) {
                logger.info("解析json失败:" + e.getMessage());
            }
        } else {
            logger.error("请求" + url + "失败,状态:" + responseEntity.getStatusCodeValue());
        }
        return response;
    }

    /**
     * get请求api cloud接口
     *
     * @param url
     * @param clazz
     * @param paramMap
     * @return
     */
    public <T> T getApiObject(String url, Class<T> clazz, Map<String, ?> paramMap) {
        logger.info("请求url:" + url + ",入参:" + gson.toJson(paramMap, Map.class));
        ResponseEntity<String> responseEntity;
        StringBuffer sb = new StringBuffer("?");
        for(Map.Entry<String, ?> entry : paramMap.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String getUrl = url + sb.toString().substring(0, sb.length() - 1);
        responseEntity = httpRestTemplate.getForEntity(getUrl, String.class);
        T response = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("请求" + url + "成功,状态:" + responseEntity.getStatusCodeValue() + ",响应body:" + responseEntity.getBody());
            if ("".equals(responseEntity.getBody())) {
                return null;
            }
            try {
                response = gson.fromJson(responseEntity.getBody(), clazz);
            } catch (Exception e) {
                logger.info("解析json失败:" + e.getMessage());
            }
        } else {
            logger.error("请求" + url + "失败,状态:" + responseEntity.getStatusCodeValue());
        }
        return response;
    }

//    --------------------------------https----------------------------------

    /**
     * https post请求api cloud接口
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T postApiObjectHttps(String url, Class<T> clazz, Object object) {
        logger.info("请求url:" + url + ",入参:" + gson.toJson(object));
        ResponseEntity<String> responseEntity = httpsRestTemplate.postForEntity(url, object, String.class);
        T response = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("请求" + url + "成功,状态:" + responseEntity.getStatusCodeValue() + ",响应body:" + responseEntity.getBody());
            if ("".equals(responseEntity.getBody())) {
                return null;
            }
            try {
                response = gson.fromJson(responseEntity.getBody(), clazz);
            } catch (Exception e) {
                logger.info("解析json失败:" + e.getMessage());
            }
        } else {
            logger.error("请求" + url + "失败,状态:" + responseEntity.getStatusCodeValue());
        }
        return response;
    }

    /**
     * Https get请求api cloud接口
     *
     * @param url
     * @param clazz
     * @param paramMap
     * @return
     */
    public <T> T getApiObjectHttps(String url, Class<T> clazz, Map<String, ?> paramMap) {
        logger.info("请求url:" + url + ",入参:" + gson.toJson(paramMap, Map.class));
        ResponseEntity<String> responseEntity;
        StringBuffer sb = new StringBuffer("?");
        for(Map.Entry<String, ?> entry : paramMap.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String getUrl = url + sb.toString().substring(0, sb.length() - 1);
        responseEntity = httpsRestTemplate.getForEntity(getUrl, String.class);
        T response = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("请求" + url + "成功,状态:" + responseEntity.getStatusCodeValue() + ",响应body:" + responseEntity.getBody());
            if ("".equals(responseEntity.getBody())) {
                return null;
            }
            try {
                response = gson.fromJson(responseEntity.getBody(), clazz);
            } catch (Exception e) {
                logger.info("解析json失败:" + e.getMessage());
            }
        } else {
            logger.error("请求" + url + "失败,状态:" + responseEntity.getStatusCodeValue());
        }
        return response;
    }
}
