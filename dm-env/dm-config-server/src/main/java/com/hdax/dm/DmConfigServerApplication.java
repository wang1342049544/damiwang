package com.hdax.dm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class DmConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmConfigServerApplication.class,args);
    }
}
