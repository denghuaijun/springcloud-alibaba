package com.denghuaijun.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Product
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 17:49
 * @Description 商品
 */
@Entity(name = "shop_product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;//商品主键
    private String pname;//商品名称
    private Double pprice;//商品价格
    private Integer stock;//库存
}
