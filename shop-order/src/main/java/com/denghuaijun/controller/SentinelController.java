package com.denghuaijun.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.denghuaijun.service.impl.OrderSentinelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SentinelController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/18 16:19
 * @Description 测试Sentinel熔断限流功能
 */
@RestController
@Slf4j
public class SentinelController {

    @Autowired
    private OrderSentinelServiceImpl orderSentinelService;

    @RequestMapping("/order/msg1")
    public String message1(){
        System.out.println("msg1");
        return "message1";
    }

    @RequestMapping("/order/msg2")
    public String message2(){
        System.out.println("msg2");
        return "message2";
    }

    //===================测试流控规则的  链路模式============
    @RequestMapping("/order/msg3")
    public String message3(){
       orderSentinelService.message();
        return "message3";
    }

    @RequestMapping("/order/msg4")
    public String message4(){
        orderSentinelService.message();
        return "message4";
    }
    //===================测试热点规则============
    @RequestMapping("/order/msg5")
    @SentinelResource("message5")//热点规则必须加这个注解否则热点规则不生效
    public String message5(String name,Integer age){

        return name+"--"+age;
    }
    //===================测试授权规则http://192.168.50.18:8091/order/msg1?serviceName=pc============

    int i =0 ;

    @RequestMapping("/order/msg6")
    public String message6(){
        i++;
        //异常比例为0.33
        if (i % 3 == 0){
            throw new RuntimeException();
        }
        return "异常比例熔断降级"+i;
    }

}
