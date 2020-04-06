package com.xd.kobepmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xd.kobepmq.mapper")
@ComponentScan(basePackages = {"com.xd.kobepcommon", "com.xd.kobepmq"})
public class KobepMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(KobepMqApplication.class, args);
    }

}
