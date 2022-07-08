package com.denghuaijun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * UserApp
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 17:57
 * @Description 用户模块主启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class,args);
    }
}
