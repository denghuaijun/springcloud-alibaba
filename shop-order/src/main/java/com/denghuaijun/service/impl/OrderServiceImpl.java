package com.denghuaijun.service.impl;

import com.alibaba.fastjson.JSON;
import com.denghuaijun.dao.OrderDao;
import com.denghuaijun.entity.Order;
import com.denghuaijun.entity.Product;
import com.denghuaijun.fegins.ProductFeginSevice;
import com.denghuaijun.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderServiceImpl
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:23
 * @Description 订单服务类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductFeginSevice feginSevice;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void save(Order order) {
      orderDao.save(order);
    }


    @GlobalTransactional
    @Override
    public Order createOrder(Integer pid) {
        //1、调用商品微服务，查询商品信息
        Product product = feginSevice.getProduct(pid);
        log.info("查询到{}号商品信息：{}",pid, JSON.toJSONString(product));
        //2、下单
        Order order = new Order();
        order.setUid(1);
        order.setUsername("dhj");
        order.setPid(pid);
        order.setPname(product.getPname());
        order.setNumber(1);
        this.save(order);
        log.info("创建订单成功，订单信息为:{}",JSON.toJSONString(order));
        //3、调用商品服务扣库存
        feginSevice.reduceInventory(pid,order.getNumber());
        //4、向MQ中投递一个下单成功的消息
        rocketMQTemplate.convertAndSend("order-topic",order);
        return order;
    }
}
