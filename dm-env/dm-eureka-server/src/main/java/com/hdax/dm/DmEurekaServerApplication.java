package com.hdax.dm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //启动注册中心
public class DmEurekaServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(DmEurekaServerApplication.class ,args);
    }
}
