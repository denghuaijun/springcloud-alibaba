package com.denghuaijun.dao;

import com.denghuaijun.entity.Product;
import com.denghuaijun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProductDao
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 18:27
 * @Description UserDao
 */
public interface UserDao extends JpaRepository<User,Integer> {
}
