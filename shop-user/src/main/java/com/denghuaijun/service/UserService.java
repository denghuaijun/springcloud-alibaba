package com.denghuaijun.service;

import com.denghuaijun.entity.Product;
import com.denghuaijun.entity.User;

/**
 * ProductService
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 18:33
 * @Description 用户业务接口
 */
public interface UserService {
    User findByUid(Integer id);
}
