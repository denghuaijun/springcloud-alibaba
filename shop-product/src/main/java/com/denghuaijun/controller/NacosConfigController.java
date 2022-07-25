package com.denghuaijun.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NacosConfigController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/25 10:48
 * @Description 使用注解的方式动态刷新获取nacos中的配置
 */
@RestController
@RefreshScope //只需要在需要动态获取配置的类上加上此注解皆可
public class NacosConfigController {

    @Value("${config.appName}")
    private String appName;

    /**
     * 硬编码方式动态读取nacos配置
     * @return
     */
    @GetMapping("/getNacosConfigByAutiware")
    public String nacosConfigTest(){
        return appName;
    }

    /**
     * 同一个微服务不同环境共享配置测试
     * @return
     */
    @GetMapping("/getNacosShareConfig")
    public String nacosConfigTest2(){
        return appName;
    }
}
