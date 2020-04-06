package com.xd.kobepjpa;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = MybatisPlusAutoConfiguration.class)
@ComponentScan(basePackages = {"com.xd.kobepcommon", "com.xd.kobepjpa"})
@EnableDiscoveryClient
@EnableFeignClients
public class KobepJpaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KobepJpaApplication.class, args);

        String userName = applicationContext.getEnvironment().getProperty("pxd.name");
        System.err.println("pxd name :"+userName);


    }




}
