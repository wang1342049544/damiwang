package com.hdax.dm;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.elasticsearch.client.RestHighLevelClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * 注册的时候需要把mybaits 部分完成
 * 所以加入其他的依赖了
 */
@SpringBootApplication
@EnableEurekaClient
//mybatis的组件扫描 因为接口被定义好了  com.hdax.dm.dao.itemtype
@MapperScan(basePackages = {"com.hdax.dm.dao"})
public class DmltemProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmltemProviderApplication.class,args);
    }
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return  mybatisPlusInterceptor;//分页插件
    }
    //es
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        ClientConfiguration configuration = ClientConfiguration
                .builder()
                .connectedTo("192.168.42.100:9200")
                //.withBasicAuth("")
                .build();;
        return RestClients.create(configuration).rest();
    }
}
