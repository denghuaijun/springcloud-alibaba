package com.denghuaijun.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

/**
 * OrderSentinelBlockImpl2BlockHandlerClass
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/13 10:44
 * @Description 限流配置类
 */
@Slf4j
public class OrderSentinelBlockImpl2BlockHandlerClass {
//注意这里必须使用static修饰方法
    public static String blockHandler(BlockException e){
        //1、编写限流处理方法
        log.error("您已被限流！-->{}",e);
        return "接口已经被限流了....";
    }
}
