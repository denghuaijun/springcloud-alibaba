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

    @Override
    public void reduceInventory(Integer pid, Integer num) {
        Product product = productDao.findById(pid).get();
        //模拟异常
        if (product.getStock()<num){
            throw new RuntimeException("库存不足！");
        }
        int i = 1/0;
        product.setStock(product.getStock()-num);
        productDao.save(product);
    }
}
