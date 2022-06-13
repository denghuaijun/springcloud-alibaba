package com.denghuaijun.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * OrderServiceImpl
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:23
 * @Description 测试sentinel 流控规则的 链路模式
 */
@Service
public class OrderSentinelServiceImpl  {

    @SentinelResource("message")
    public void message(){
        System.out.println("上级接口----->message");
    }
}
