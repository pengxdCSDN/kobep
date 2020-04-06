package com.xd.kobepgateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: url白名单处理, application.yml中配置需要放权的url白名单
 * @author: pxd
 * @create: 2018-11-27 11:17
 **/

@ConfigurationProperties(prefix = "xd.permit")
@Component
public class KobepPermitUrlProperties {
    /**
     * 监控中心,swagger和hystrix需要访问的url,需要预览文件的url
     */
    private static final String[] IGNORE_ENDPOINTS = {

            "/**/actuator/**",
            "/**/v2/api-docs/**", "/**/swagger-ui.html", "/**/swagger-resources/**",
            "/**/custom/error/**","/**/css/**","/**/images/**","/**/js/**","/**/pdfjs/**","/**/plyr/**",
    };

    private String[] oauth2Urls;

    public String[] getOauth2Urls() {
        if (StringUtils.isEmpty(oauth2Urls) || oauth2Urls.length == 0) {
            return IGNORE_ENDPOINTS;
        }

        List<String> list = new ArrayList<>();
        for (String url : IGNORE_ENDPOINTS) {
            list.add(url);
        }
        for (String url : oauth2Urls) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }


    /**
     * 需要放开权限的url
     *
     * @param oauth2Urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */

    public void setOauth2Urls(String[] oauth2Urls) {
        this.oauth2Urls = oauth2Urls;
    }





}
