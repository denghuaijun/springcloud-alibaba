package com.denghuaijun.service.impl;

import com.denghuaijun.dao.OrderDao;
import com.denghuaijun.entity.Order;
import com.denghuaijun.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderServiceImpl
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:23
 * @Description 订单服务类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    @Override
    public void save(Order order) {
      orderDao.save(order);
    }
}
