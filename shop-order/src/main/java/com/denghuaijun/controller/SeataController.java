package com.denghuaijun.controller;

import com.denghuaijun.entity.Order;
import com.denghuaijun.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * SeataController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/25 14:09
 * @Description 测试使用seata分布式事务demo
 */
@RestController
@Slf4j
public class SeataController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/seata/order/{pid}")
    public Order order(@PathVariable("pid")Integer pid){
        return orderService.createOrder(pid);
    }
}
