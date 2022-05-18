package com.denghuaijun.dao;

import com.denghuaijun.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderDao
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:21
 * @Description 订单dao
 */
public interface OrderDao extends JpaRepository<Order,Long> {
}
