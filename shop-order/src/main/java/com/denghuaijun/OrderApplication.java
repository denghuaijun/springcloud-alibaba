package com.denghuaijun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * OrderApplication
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:19
 * @Description 订单启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients//开启fegin组件
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Bean
    //使用Ribbon调用服务名的方式实现负载均衡
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
