package com.xd.kobepgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class KobepGatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(KobepGatewayApplication.class, args);
    }

}
