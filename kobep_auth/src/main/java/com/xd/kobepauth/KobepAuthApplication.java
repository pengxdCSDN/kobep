package com.xd.kobepauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.xd.kobepcommon", "com.xd.kobepauth"})
@MapperScan("com.xd.kobepauth.mapper")
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class KobepAuthApplication {



    @RequestMapping("/aaa")
    public String ss() throws Exception{



        return "aaa";
    }


    public static void main(String[] args) {
        SpringApplication.run(KobepAuthApplication.class, args);
    }

}
