package com.denghuaijun.dao;

import com.denghuaijun.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProductDao
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 18:27
 * @Description ProductDao
 */
public interface ProductDao extends JpaRepository<Product,Integer> {
}
