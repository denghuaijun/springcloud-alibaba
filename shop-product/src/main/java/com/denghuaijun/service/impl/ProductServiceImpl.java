package com.denghuaijun.service.impl;

import com.denghuaijun.dao.ProductDao;
import com.denghuaijun.entity.Product;
import com.denghuaijun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductServiceImpl
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 18:34
 * @Description 业务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product findByPid(Integer id) {
        return productDao.findById(id).get();
    }
}
