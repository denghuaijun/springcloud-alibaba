package com.denghuaijun.config;

import lombok.extern.slf4j.Slf4j;

/**
 * OrderSentinelBlockImpl2BlockHandlerClass
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/13 10:44
 * @Description 异常配置类
 */
@Slf4j
public class OrderSentinelBlockImpl2FallBackClass {
//注意这里必须使用static修饰方法
    public static String fallBack(Throwable e){
        //1、编写异常处理方法
        log.error("您已异常！-->{}",e);
        return "接口已经出现异常....";
    }
}
