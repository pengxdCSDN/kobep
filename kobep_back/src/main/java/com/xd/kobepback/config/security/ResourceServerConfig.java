package com.xd.kobepback.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 
 * @author pxd
 * data 2018-6-04
 * 设置权限资源
 * 
 * 
 */
@Configuration
@EnableResourceServer
//@Order(Integer.MAX_VALUE - 11)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    private Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);


    @Autowired(required = false)
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter tokenConverter;
    
    private static final String[] PAST_TOKEN_CHECK =  new String[]{

            "/**"

    };



    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PAST_TOKEN_CHECK).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("kobep").tokenStore(tokenStore);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
