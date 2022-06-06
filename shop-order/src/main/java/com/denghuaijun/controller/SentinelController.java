package com.denghuaijun.controller;

import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping("/order/msg1")
    public String message1(){
        return "message1";
    }

    @RequestMapping("/order/msg2")
    public String message2(){
        return "message2";
    }
}
