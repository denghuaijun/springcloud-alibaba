package com.denghuaijun.config;

import com.denghuaijun.entity.Product;
import com.denghuaijun.fegins.ProductFeginSevice;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ProductServiceFallBack
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/13 14:13
 * @Description fegins整合sentinel创建容错类，如果想在容错类中拿到具体的错误
 */
@Slf4j
@Component
public class ProductServiceFallBackFactory implements FallbackFactory<ProductFeginSevice> {

    @Override
    public ProductFeginSevice create(Throwable throwable) {
        return new ProductFeginSevice() {
            @Override
            public Product getProduct(Integer pid) {
                log.error("调用商品服务发生异常：{}",throwable.getMessage());
                Product product = new Product();
                product.setPid(-1);
                return product;
            }
        };
    }
}
