package com.hdax.dm;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;


/**
 * 商品微服务
 */

/**
 * 所有的数据不是调用提供者来访问的  测试完成之后
 * 提供者所对应的端口号是不允许开放的
 * 比如提供端口8034  那么防火墙不会开放8034接口
 * 访问其他端口就可以 因为他们是消费者 就是对外的
 *
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 加上exclude  就把 mysql依赖省略了
@EnableEurekaClient
//开启声明式的远程调用
@EnableFeignClients
public class DmltemConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmltemConsumerApplication.class,args);
    }
    @Bean
    RestHighLevelClient restHighLevelClient(){
        ClientConfiguration configuration
                = ClientConfiguration.builder()
                .connectedTo("192.168.42.100:9200")   //es端口
                .build();
        RestHighLevelClient restHighLevelClient = RestClients.create(configuration).rest();
        return restHighLevelClient;
    }
}
