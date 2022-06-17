package com.denghuaijun.controller;

import com.denghuaijun.utils.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * TestApi
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/17 13:58
 * @Description 测试
 */
@Slf4j
@RestController
public class TestApi {


    @Autowired
    RedisLock redisLock;

    @GetMapping("/test")
    public String test(){
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch threadLatch = new CountDownLatch(2);

        final int lockExpireTime =5;
        final int timeout =3;
        String lockKey ="test:lock";

        Runnable runnable = ()->{
            String lockValue = "";
            try{
                //等待发号枪零，防止线程枪跑
                start.await();
                //允许丢数据的锁
               //lockValue =  redisLock.tryLock(lockKey,lockExpireTime);
               lockValue = redisLock.tryDistibutionLock(lockKey,lockExpireTime,timeout);
               //测试业务停一会，故意让后面线程抢不到锁
                TimeUnit.SECONDS.sleep(4);
                log.info(String.format("%s get lock success,value:%s",Thread.currentThread().getName(),lockValue));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                redisLock.unLock(lockKey,lockValue);
                //执行完计数器减1
                threadLatch.countDown();
            }
        };

        Thread t1 = new Thread(runnable,"t1");
        Thread t2 = new Thread(runnable,"t2");
        t1.start();
        t2.start();
        //预备：开始
        start.countDown();
        //等待所有线程跑完
        try {
            threadLatch.await();
            log.info("====================done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "test";

    }}
