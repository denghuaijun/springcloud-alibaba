package com.denghuaijun.config;

import com.denghuaijun.entity.Product;
import com.denghuaijun.fegins.ProductFeginSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ProductServiceFallBack
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/13 14:13
 * @Description fegins整合sentinel创建容错类，容错类必须要实现被容错接口
 */
@Slf4j
@Component
public class ProductServiceFallBack implements ProductFeginSevice {
    @Override
    public Product getProduct(Integer pid) {
      Product product = new Product();
      product.setPid(-1);
        return product;
    }

    @Override
    public void reduceInventory(Integer pid, Integer number) {
        log.error("调用商品服务扣减库存发生异常");
    }
}
