package com.denghuaijun.service;

import com.denghuaijun.entity.Order;

/**
 * OrderService
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:22
 * @Description 订单服务类
 */
public interface OrderService {

    public void save(Order order);

    //创建订单然后在扣减库存
    public Order createOrder(Integer pid);

}
