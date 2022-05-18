package com.denghuaijun.service.impl;

import com.denghuaijun.dao.UserDao;
import com.denghuaijun.entity.User;
import com.denghuaijun.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUid(Integer id) {
        return userDao.findById(id).get();
    }
}
