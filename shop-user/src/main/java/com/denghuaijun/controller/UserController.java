package com.denghuaijun.controller;

import com.alibaba.fastjson.JSON;
import com.denghuaijun.entity.User;
import com.denghuaijun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/16 18:35
 * @Description 商品控制层
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{uid}")
    public User getUserById(@PathVariable("uid")Integer uid){
        User product = userService.findByUid(uid);
        log.info("查询到的用户："+ JSON.toJSONString(product));
        return product;
    }
}
