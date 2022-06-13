package com.denghuaijun.fegins;

import com.denghuaijun.config.ProductServiceFallBackFactory;
import com.denghuaijun.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ProductFeginSevice
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 17:32
 * @Description
 */
//value 用来指定调用nacos下面那个服务
// fallback用于指定容错类
// fallbackFactory 来获取发生错误的信息 与fallback俩者只能使用其一
//@FeignClient(value = "service-product",fallback = ProductServiceFallBack.class)//声明调用服务提供者的服务名
@FeignClient(value = "service-product",fallbackFactory = ProductServiceFallBackFactory.class)
public interface ProductFeginSevice {

    @GetMapping(value = "/product/{pid}")
    Product getProduct(@PathVariable("pid") Integer pid);
}
