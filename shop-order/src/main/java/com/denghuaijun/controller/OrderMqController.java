package com.denghuaijun.controller;

import com.alibaba.fastjson.JSON;
import com.denghuaijun.entity.Order;
import com.denghuaijun.entity.Product;
import com.denghuaijun.fegins.ProductFeginSevice;
import com.denghuaijun.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderMqController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/8 10:30
 * @Description 模拟下单成功，向下单用户发送短信
 * 订单微服务下单，然后订单信息使用mq的消息油用户微服务订阅，在发送短信
 * 模拟下单微服务发送消息
 */
@RestController
@Slf4j
@RequestMapping("/mq")
public class OrderMqController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductFeginSevice feginSevice;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    //准备买一件商品
    @GetMapping("/product/{pid}")
    public Order order(@PathVariable("pid") Integer pid){
        log.info(">>>>客户开始下单，准备调用商品微服务查询商品信息！");
        Product product = feginSevice.getProduct(pid);
        if (product == null){
            Order order = new Order();
            order.setPname("下单失败！");
            return order;
        }
        log.info("》》》商品信息查询结果:"+ JSON.toJSONString(product));
        Order order= new Order();
        order.setUid(1);
        order.setUsername("dhj");
        order.setPid(pid);
        order.setPname(product.getPname());
        orderService.save(order);
        //下单成功，将消息放到MQ
        rocketMQTemplate.convertAndSend("order-topic",order);
        return order;
    }
}
