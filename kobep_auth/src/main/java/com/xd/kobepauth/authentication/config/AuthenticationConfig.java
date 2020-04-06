package com.xd.kobepauth.authentication.config;

import com.xd.kobepauth.properties.AuthenticationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 注入authentication配置
 * @author: pxd
 * @create: 2019-01-21 14:20
 **/
@Configuration
@EnableConfigurationProperties(AuthenticationProperties.class)
public class AuthenticationConfig {
}
