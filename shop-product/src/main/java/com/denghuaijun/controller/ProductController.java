package com.denghuaijun.controller;

import com.alibaba.fastjson.JSON;
import com.denghuaijun.entity.Product;
import com.denghuaijun.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 18:35
 * @Description 商品控制层
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping("/product/{pid}")
    public Product productById(@PathVariable("pid")Integer pid){
        Product product = productService.findByPid(pid);
        log.info("查询到的商品："+ JSON.toJSONString(product));
        return product;
    }

    /**
     * 硬编码方式动态读取nacos配置
     * @return
     */
    @GetMapping("/getNacosConfig")
    public String nacosConfigTest(){
        return applicationContext.getEnvironment().getProperty("config.appName");
    }
}
