package com.denghuaijun.fegins;

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
@FeignClient("service-product")//声明调用服务提供者的服务名
public interface ProductFeginSevice {

    @GetMapping(value = "/product/{pid}")
    Product getProduct(@PathVariable("pid") Integer pid);
}
