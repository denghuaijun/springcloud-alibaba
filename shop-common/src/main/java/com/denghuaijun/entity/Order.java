package com.denghuaijun.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Order
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 17:51
 * @Description 订单实体类
 */
@Entity(name = "shop_order")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;//订单主键
    private Integer uid;//用户ID
    private String username;//用户名
    private Integer pid;//商品ID
    private String pname;//商品名称
    private Integer number;//商品数量
}
