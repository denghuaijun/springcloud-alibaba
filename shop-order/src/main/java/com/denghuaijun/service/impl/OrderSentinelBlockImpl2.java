package com.denghuaijun.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.denghuaijun.config.OrderSentinelBlockImpl2BlockHandlerClass;
import com.denghuaijun.config.OrderSentinelBlockImpl2FallBackClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * OrderSentinelBlockImpl
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/13 10:35
 * @Description 将限流和降级的方法定义再不同类中
 */
@Slf4j
@Service
public class OrderSentinelBlockImpl2 {
    int i=0;
    @SentinelResource(
            value = "message",
            blockHandlerClass = OrderSentinelBlockImpl2BlockHandlerClass.class,
            blockHandler = "blockHandler",
            fallbackClass = OrderSentinelBlockImpl2FallBackClass.class,
            fallback = "fallBack"
    )
    public String message(){
        i++;
        if (i % 3 ==0){
            throw new RuntimeException();
        }
        return "message";
    }

//    //定义发生异常时进入的方法1、必须时public 2、方法的返回参数类型与原方法一样，3、默认再同一个类中，或者要是有对呀的class处理注解
//    public String blockHandler(BlockException ex){
//        log.error("接口被限流了。。。{}",ex);
//        return "接口被限流了。。";
//    }
//    //发生异常时回滚处理方法
//    public String fallback(Throwable throwable){
//        log.error("接口发生异常:{}",throwable);
//        return "接口发生异常了。。。";
//    }
}
