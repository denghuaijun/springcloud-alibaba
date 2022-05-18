package com.denghuaijun.controller;

import com.alibaba.fastjson.JSON;
import com.denghuaijun.entity.Order;
import com.denghuaijun.entity.Product;
import com.denghuaijun.fegins.ProductFeginSevice;
import com.denghuaijun.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * GaoBingFaController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 17:49
 * @Description 测试高并发场景服务雪崩情况
 * 使用压测工具对服务的一个接口进行压测，然后再访问另一个接口会出现访问异常
 */
@RestController
@Slf4j
public class GaoBingFaController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductFeginSevice feginSevice;

    /**
     * 高并发测试
     * @param pid
     * @return
     */
    @RequestMapping("/order/{pid}")
    public Order getOrderByFegin (@PathVariable("pid") Integer pid){
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息");
        //通过restTemplate调用商品微服务
        //获取商品服务的服务示例
        String serviceName ="service-product";
        Product product =feginSevice.getProduct(pid);
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息:{}", JSON.toJSONString(product));
        //模拟一次网络延时
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Order order = new Order();
        order.setUid(1);
        order.setUsername("test-fegin");
        order.setPid(product.getPid());
        //orderService.save(order);
        return order;
    }

    @RequestMapping("/order/msg")
    public String message(){
        return "高并发下的问题测试";
    }

}
