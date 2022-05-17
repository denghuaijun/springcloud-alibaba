package com.denghuaijun.service;

import com.denghuaijun.entity.Product;

/**
 * ProductService
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 18:33
 * @Description 商品业务接口
 */
public interface ProductService {
    Product findByPid(Integer id);
}
