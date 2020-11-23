package com.hdax.dm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 注册的时候需要把mybaits 部分完成
 * 所以加入其他的依赖了
 */
@SpringBootApplication
@EnableEurekaClient
//mybatis的组件扫描 因为接口被定义好了  com.hdax.dm.dao.itemtype
@MapperScan(basePackages = {"com.hdax.dm.dao"})
public class  DmBaseProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmBaseProviderApplication.class,args);
    }
}
