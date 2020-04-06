package com.xd.kobepback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan("com.xd.kobepback.mapper")
@ComponentScan(basePackages = {"com.xd.kobepcommon", "com.xd.kobepback"})
@EnableDiscoveryClient
@EnableFeignClients
public class KobepBackApplication {




    public static void main(String[] args) {
        SpringApplication.run(KobepBackApplication.class, args);


    }
}

